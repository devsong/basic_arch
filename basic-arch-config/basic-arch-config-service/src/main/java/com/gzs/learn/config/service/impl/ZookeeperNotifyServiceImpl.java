package com.gzs.learn.config.service.impl;

import static com.gzs.learn.config.IConfigConstant.CONFIG_FORMAT;
import static com.gzs.learn.config.IConfigConstant.ZK_GROUP;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent.Type;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.gzs.learn.common.util.BeanUtil;
import com.gzs.learn.common.util.SimpleTemplateUtil;
import com.gzs.learn.config.IConfigConstant;
import com.gzs.learn.config.inf.SysConfigDto;
import com.gzs.learn.config.inf.SysConfigKeyDto;
import com.gzs.learn.config.service.ZookeeperNotifyService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ZookeeperNotifyServiceImpl implements ZookeeperNotifyService {
    private static final int DEFAULT_CACHE_LEN = 1024;
    private PathChildrenCache groupChildrenCache;
    /**
     * ZooKeeper客户端
     */
    private CuratorFramework client;
    private Map<String, String> cache = new ConcurrentHashMap<String, String>(DEFAULT_CACHE_LEN);

    public ZookeeperNotifyServiceImpl(String connect, int timeOut, String path) {
        this.client = CuratorFrameworkFactory.builder().connectString(connect).namespace(path)
                .retryPolicy(new RetryNTimes(Integer.MAX_VALUE, 1000)).connectionTimeoutMs(timeOut).build();
        this.client.start();
        log.info("init ZooKeeper Client Success!");
    }

    @SuppressWarnings("deprecation")
    @PostConstruct
    public void init() {
        this.groupChildrenCache = new PathChildrenCache(client, IConfigConstant.ZK_GROUP, true);
        groupChildrenCache.getListenable().addListener(configListener);
        try {
            groupChildrenCache.start(StartMode.NORMAL);
        } catch (Exception e) {
            log.error("zk config listener init failed");
        }
    }

    @Override
    public boolean createNode(SysConfigDto sysConfigDto) {
        try {
            String keyName = getKey(sysConfigDto);
            byte[] data = JSON.toJSONString(sysConfigDto).getBytes();
            if (this.client.checkExists().forPath(keyName) == null) {
                // not exists,insert
                this.client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(keyName, data);
            } else {
                // exists, update
                this.client.setData().forPath(keyName, data);
            }
            return true;
        } catch (Exception e) {
            log.error("create node error", e);
            return false;
        }
    }

    @Override
    public boolean deleteNode(SysConfigDto sysConfigDto) {
        try {
            String keyName = getKey(sysConfigDto);
            if (this.client.checkExists().forPath(keyName) != null) {
                this.client.delete().deletingChildrenIfNeeded().forPath(keyName);
            }
            return true;
        } catch (Exception e) {
            log.error("delete node error", e);
            return false;
        }
    }

    private PathChildrenCacheListener configListener = new PathChildrenCacheListener() {
        List<PathChildrenCacheEvent.Type> filters = Lists.newArrayList(Type.CHILD_ADDED, Type.CHILD_UPDATED, Type.CHILD_REMOVED);

        @Override
        public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
            if (filters.contains(event.getType())) {
                byte[] data = event.getData().getData();
                SysConfigDto configDto = JSON.parseObject(new String(data), SysConfigDto.class);
                switch (event.getType()) {
                case CHILD_ADDED:
                    addSysConfig(configDto);
                    break;
                case CHILD_UPDATED:
                    updateSysConfig(configDto);
                    break;
                case CHILD_REMOVED:
                    delSysConfig(configDto);
                    break;
                default:
                    break;
                }
            }
        }
    };

    private void addSysConfig(SysConfigDto configDto) {
        String key = getKey(configDto);
        cache.put(key, JSON.toJSONString(configDto));
    }

    private void updateSysConfig(SysConfigDto configDto) {
        String key = getKey(configDto);
        cache.put(key, JSON.toJSONString(configDto));
    }

    private void delSysConfig(SysConfigDto configDto) {
        String key = getKey(configDto);
        cache.remove(key);
    }

    private String getKey(SysConfigDto sysConfigDto) {
        Map<String, String> params = Maps.newHashMap();
        params.put("product", sysConfigDto.getProduct());
        params.put("groupName", sysConfigDto.getGroupName());
        params.put("app", sysConfigDto.getApp());
        params.put("configKey", sysConfigDto.getConfigKey());
        String keyName = ZK_GROUP + SimpleTemplateUtil.processTemplate(CONFIG_FORMAT, params);
        return keyName;
    }

    @Override
    public String getConfig(SysConfigKeyDto key) {
        SysConfigDto sysConfigDto = new SysConfigDto();
        BeanUtil.copyProperties(key, sysConfigDto);
        String configKey = getKey(sysConfigDto);
        String configVal = cache.get(configKey);
        if (StringUtils.isNotBlank(configVal)) {
            return configVal;
        }
        try {
            configVal = new String(client.getData().forPath(configKey));
            cache.put(configKey, configVal);
            return configVal;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

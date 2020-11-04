package com.gzs.learn.serial.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent.Type;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.gzs.learn.common.util.JsonUtil;
import com.gzs.learn.serial.ISerialConst;
import com.gzs.learn.serial.conf.SerialProperties;
import com.gzs.learn.serial.domain.GroupListenerNode;
import com.gzs.learn.serial.inf.pk.SerialGroupPK;
import com.gzs.learn.serial.service.SerialUpdateService;
import com.gzs.learn.serial.service.ZookeeperNotifyService;
import com.gzs.learn.serial.type.NodeType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ZookeeperNotifyServiceImpl implements ZookeeperNotifyService {
    private final static String PATH = "com.gzs.learn.serial";
    private final static String PARTITION_FORMAT = ISerialConst.PARTITION + "/%s,%d,%d";
    private final static String PATH_FORMAT = ISerialConst.GROUP + "/%s_%d";
    private String notifyZk;
    /**
     * ZooKeeper客户端
     */
    private CuratorFramework client;
    /**
     *
     */
    private PathChildrenCache groupChildrenCache;

    @Autowired
    private SerialUpdateService serialUpdateService;

    @Autowired
    private SerialProperties serialProperties;

    @SuppressWarnings("deprecation")
    @PostConstruct
    public void init() throws Exception {
        this.notifyZk = serialProperties.getNotifyZk();
        this.client = CuratorFrameworkFactory.builder().connectString(notifyZk).namespace(PATH)
                .retryPolicy(new RetryNTimes(Integer.MAX_VALUE, 1000)).connectionTimeoutMs(10000).build();
        this.client.start();
        log.info("Init ZooKeeper Client Success!");
        this.groupChildrenCache = new PathChildrenCache(client, ISerialConst.GROUP, true);
        groupChildrenCache.getListenable().addListener(serialGroupListener);
        groupChildrenCache.start(StartMode.NORMAL);
    }

    @Override
    public boolean tryLock(String name, int version, int partition) {
        boolean ret = false;
        String path = String.format(PARTITION_FORMAT, name, version, partition);
        try {
            if (this.client.create().withMode(CreateMode.EPHEMERAL).forPath(path) != null) {
                ret = true;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return ret;
    }

    private PathChildrenCacheListener serialGroupListener = new PathChildrenCacheListener() {
        List<PathChildrenCacheEvent.Type> filters = Lists.newArrayList(Type.CHILD_ADDED, Type.CHILD_REMOVED);

        @Override
        public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
            if (filters.contains(event.getType())) {
                byte[] data = event.getData().getData();
                SerialGroupPK pk = JsonUtil.parseObject(new String(data), SerialGroupPK.class);
                switch (event.getType()) {
                case CHILD_ADDED:
                    serialUpdateService.addQueue(new GroupListenerNode(pk.getName(), pk.getVersion(), 0L, NodeType.ADD));
                    break;
                case CHILD_REMOVED:
                    serialUpdateService.addQueue(new GroupListenerNode(pk.getName(), pk.getVersion(), 0L, NodeType.REMOVE));
                    break;
                default:
                    break;
                }
            }
        }
    };

    @Override
    public boolean ceateNode(SerialGroupPK primaryKey) {
        try {
            String path = String.format(PATH_FORMAT, primaryKey.getName(), primaryKey.getVersion());
            if (this.client.checkExists().forPath(path) == null) {
                this.client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path,
                        JsonUtil.toJSONString(primaryKey).getBytes());
            }
            return true;
        } catch (Exception e) {
            log.error("create node error", e);
            return false;
        }
    }

    @Override
    public boolean deleteNode(SerialGroupPK primaryKey) {
        try {
            String path = String.format(PATH_FORMAT, primaryKey.getName(), primaryKey.getVersion());
            if (this.client.checkExists().forPath(path) != null) {
                this.client.delete().deletingChildrenIfNeeded().forPath(path);
            }
            return true;
        } catch (Exception e) {
            log.error("delete node error", e);
            return false;
        }
    }
}

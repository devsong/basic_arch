package com.gzs.learn.serial.service.imp;

import java.util.List;

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

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.gzs.learn.serial.SerialConsts;
import com.gzs.learn.serial.conf.SerialProperties;
import com.gzs.learn.serial.domain.GroupListenerNode;
import com.gzs.learn.serial.inf.pk.SerialGroupPK;
import com.gzs.learn.serial.service.SerialUpdateService;
import com.gzs.learn.serial.service.ZookeeperNotifyService;
import com.gzs.learn.serial.type.NodeType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ZookeeperNotifyServiceImp implements ZookeeperNotifyService {
    private final static String PATH = "com.gzs.learn.serial";
    private final static String PARTITION_FORMAT = SerialConsts.PARTITION + "/%s,%d,%d";
    private String notifyZk;
    /**
     * ZooKeeper客户端
     */
    private CuratorFramework client;
    /**
     *
     */
    private PathChildrenCache groupChildrenCache;

    private SerialUpdateService serialUpdateService;

    @Autowired
    public ZookeeperNotifyServiceImp(SerialProperties serialProperties) {
        super();
        this.notifyZk = serialProperties.getNotifyZk();
        this.client = CuratorFrameworkFactory.builder().connectString(notifyZk).namespace(PATH)
                .retryPolicy(new RetryNTimes(Integer.MAX_VALUE, 1000)).connectionTimeoutMs(10000).build();
        this.client.start();
        log.info("Init ZooKeeper Client Success!");
    }

    @SuppressWarnings("deprecation")
    @Override
    public void init(SerialUpdateService serialUpdateService) throws Exception {
        this.serialUpdateService = serialUpdateService;
        this.groupChildrenCache = new PathChildrenCache(client, SerialConsts.GROUP, true);
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
                SerialGroupPK pk = JSON.parseObject(new String(data), SerialGroupPK.class);
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
}

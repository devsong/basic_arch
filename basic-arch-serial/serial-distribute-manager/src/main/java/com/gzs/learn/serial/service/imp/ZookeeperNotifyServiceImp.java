package com.gzs.learn.serial.service.imp;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;

import com.alibaba.fastjson.JSON;
import com.gzs.learn.serial.SerialConsts;
import com.gzs.learn.serial.inf.pk.SerialGroupPK;
import com.gzs.learn.serial.service.ZookeeperNotifyService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ZookeeperNotifyServiceImp implements ZookeeperNotifyService {
    private final static String PATH_FORMAT = SerialConsts.GROUP + "/%s_%d";
    /**
     * ZooKeeper客户端
     */
    private CuratorFramework client;

    public ZookeeperNotifyServiceImp(String connect, int timeOut, String path) {
        super();
        this.client = CuratorFrameworkFactory.builder().connectString(connect).namespace(path)
                .retryPolicy(new RetryNTimes(Integer.MAX_VALUE, 1000)).connectionTimeoutMs(timeOut).build();
        this.client.start();
        log.info("Init ZooKeeper Client Success!");
    }

    @Override
    public boolean ceateNode(SerialGroupPK primaryKey) {
        try {
            String path = String.format(PATH_FORMAT, primaryKey.getName(), primaryKey.getVersion());
            if (this.client.checkExists().forPath(path) == null) {
                this.client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path,
                        JSON.toJSONString(primaryKey).getBytes());
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

package com.gzs.learn.serial.service;

public interface ZookeeperNotifyService {
    boolean tryLock(String name, int version, int partition);
}

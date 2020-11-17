package com.gzs.learn.serial.service;

import java.util.concurrent.LinkedBlockingQueue;

import com.gzs.learn.serial.domain.GroupListenerNode;
import com.gzs.learn.serial.exception.SerialException;
import com.gzs.learn.serial.inf.SerialGroup;
import com.gzs.learn.serial.inf.pk.SerialGroupPK;

public interface SerialManagerService {

    void rechargeSerialGroup(String name, int version, long tsup);

    void createSerialGroup(String name, int version);

    /**
     * 操作队列
     * @return
     */
    LinkedBlockingQueue<GroupListenerNode> getOperatorQueue();

    long directSerialGroup(String name, int length);

    /**
     * 创建分组
     * 
     * @param group
     * @return
     * @throws SerialException
     */
    SerialGroupPK createSerialGroup(SerialGroup group) throws SerialException;
}

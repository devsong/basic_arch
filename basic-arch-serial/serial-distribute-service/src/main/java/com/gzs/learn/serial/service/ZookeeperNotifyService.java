package com.gzs.learn.serial.service;

import com.gzs.learn.serial.inf.pk.SerialGroupPK;

public interface ZookeeperNotifyService {
    /**
     * 锁定指定节点
     * 
     * @param name
     * @param version
     * @param partition
     * @return
     */
    boolean tryLock(String name, int version, int partition);

    /**
     * 创建节点
     * 
     * @param serialPk
     * @return
     */
    public boolean ceateNode(SerialGroupPK serialPk);

    /**
     * 删除节点
     * 
     * @param serialPk
     * @return
     */
    public boolean deleteNode(SerialGroupPK serialPk);
}

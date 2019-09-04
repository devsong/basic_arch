package com.gzs.learn.serial.service;

import com.gzs.learn.serial.exception.SerialException;
import com.gzs.learn.serial.inf.SerialGroup;
import com.gzs.learn.serial.inf.pk.SerialGroupPK;

public interface SerialManagerService {
    /**
     * 创建分组
     * 
     * @param group
     * @return
     * @throws SerialException
     */
    SerialGroupPK createSerialGroup(SerialGroup group) throws SerialException;

    void init();
}

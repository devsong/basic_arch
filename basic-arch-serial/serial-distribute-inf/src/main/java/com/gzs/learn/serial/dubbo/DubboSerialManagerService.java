package com.gzs.learn.serial.dubbo;

import com.gzs.learn.serial.exception.SerialException;
import com.gzs.learn.serial.inf.SerialGroup;

public interface DubboSerialManagerService {
    void createSerialGroup(SerialGroup group) throws SerialException;
}

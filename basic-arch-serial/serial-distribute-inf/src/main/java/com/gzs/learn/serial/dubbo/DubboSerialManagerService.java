package com.gzs.learn.serial.dubbo;

import com.gzs.learn.inf.PageResponseDto;
import com.gzs.learn.serial.exception.SerialException;
import com.gzs.learn.serial.inf.SerialGroup;
import com.gzs.learn.serial.inf.SerialGroupSearch;
import com.gzs.learn.serial.inf.SerialPartition;

public interface DubboSerialManagerService {
    void createSerialGroup(SerialGroup group) throws SerialException;

    PageResponseDto<SerialGroup> getSerialGroup(SerialGroupSearch search);

    PageResponseDto<SerialPartition> getSerialPartition(long groupId);
}

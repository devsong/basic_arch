package com.gzs.learn.serial.dubbo;

import com.gzs.learn.inf.PageResponseDto;
import com.gzs.learn.serial.exception.SerialException;
import com.gzs.learn.serial.inf.SerialGroup;
import com.gzs.learn.serial.inf.SerialGroupSearch;
import com.gzs.learn.serial.inf.SerialPartition;
import com.gzs.learn.serial.inf.SerialSnowflakeInfo;

public interface DubboSerialManagerService {
    /**
     * 创建序列号组
     * @param group
     * @throws SerialException
     */
    void createSerialGroup(SerialGroup group) throws SerialException;

    /**
     * 查询segment group
     * @param search
     * @return
     */
    PageResponseDto<SerialGroup> getSerialGroup(SerialGroupSearch search);

    /**
     * 查询segment 分区信息
     * @param groupId
     * @return
     */
    PageResponseDto<SerialPartition> getSerialPartition(long groupId);

    /**
     * snowflake解码
     * @param serialId
     * @return
     */
    SerialSnowflakeInfo decodeSnowflake(long serialId);
}

package com.gzs.learn.serial.inf;

import java.io.Serializable;

import lombok.Data;

@Data
public class SerialSnowflakeInfo implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    // 时间戳
    private long timestamp;
    // 时间字符串格式
    private String time;
    // 数据中心ID
    private long dataCenterId;
    // workerID
    private long workerId;
    // 序列号
    private long seq;
}

package com.ruoyi.serial.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class SerialSnowflakeInfo implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    // 时间戳
    private String timestamp;
    // 时间字符串格式
    private String time;
    // 数据中心ID
    private int dataCenterId;
    // workerID
    private int workerId;
    // 序列号
    private int seq;
}

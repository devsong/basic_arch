package com.ruoyi.serial.dto;

import lombok.Data;

@Data
public class SerialAllocDto {
    /**
     * 业务键值
     */
    private String key;
    /**
     * 以使用的最大ID
     */
    private String maxId;
    /**
     * 步长
     */
    private int step;
    /**
     * 尾部随机数位数
     */
    private int randomLen;
    /**
     * 描述
     */
    private String description;
    /**
     * 状态
     */
    private Integer status;
}

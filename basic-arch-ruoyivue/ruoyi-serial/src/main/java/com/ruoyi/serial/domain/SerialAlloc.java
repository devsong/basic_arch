package com.ruoyi.serial.domain;

import java.util.Date;

import lombok.Data;

@Data
public class SerialAlloc {
    /**
     * 业务键值
     */
    private String key;
    /**
     * 以使用的最大ID
     */
    private long maxId;
    /**
     * 步长
     */
    private int step;
    /**
     * 描述
     */
    private String description;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

}

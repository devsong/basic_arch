package com.gzs.learn.config.inf;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class ConfigDto implements Serializable {
    /**
    * 
    */
    private static final long serialVersionUID = 1L;
    /**
     * 配置id
     */
    private Long id;
    /**
     * 产品线
     */
    private String product;
    /**
     * 分组
     */
    private String group;
    /**
     * 应用名
     */
    private String app;
    /**
     * 配置名称
     */
    private String configName;
    /**
     * 配置值
     */
    private String configValue;
    /**
     * 版本号
     */
    private Integer version;
    /**
     * 创建人
     */
    private String creator;
    /**
     * 最后一次更新人
     */
    private String lastUpdator;
    /**
     * 最后一次更新时间
     */
    private Date lastUpdateTime;
    /**
     * 更新ip
     */
    private String lastUpdateIp;
}

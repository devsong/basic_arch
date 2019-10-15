package com.gzs.learn.config.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Table(name = "sys_config")
@Data
public class SysConfigPo implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @Id
    private Long id;
    /**
     * 产品线
     */
    private String product;
    /**
     * 组名
     */
    @Column(name = "group_name")
    private String groupName;
    /**
     * 应用名
     */
    private String app;
    /**
     * 配置描述
     */
    @Column(name = "config_name")
    private String configName;
    /**
     * 配置键值
     */
    @Column(name = "config_key")
    private String configKey;
    /**
     * 配置值
     */
    @Column(name = "config_value")
    private String configValue;
    /**
     * 配置状态
     */
    private Integer status;
    /**
     * 版本号
     */
    private Integer version;
    /**
     * 创建人
     */
    private String creator;
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
    /**
     * 最近一次更新人
     */
    @Column(name = "last_updator")
    private String lastUpdator;
    /**
     * 最近一次更新时间
     */
    @Column(name = "last_update_time")
    private Date lastUpdateTime;
    /**
     * 最近一次更新IP
     */
    @Column(name = "last_update_ip")
    private String lastUpdateIp;
}

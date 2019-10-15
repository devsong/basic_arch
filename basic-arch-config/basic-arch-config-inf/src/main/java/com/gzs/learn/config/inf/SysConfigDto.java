package com.gzs.learn.config.inf;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SysConfigDto implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private Long id;
    /**
     * 产品线
     */
    private String product;
    /**
     * 组名
     */
    private String groupName;
    /**
     * 应用名
     */
    private String app;
    /**
     * 配置描述
     */
    private String configName;
    /**
     * 配置键值
     */
    private String configKey;
    /**
     * 配置值
     */
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
    private Date createTime;
    /**
     * 最近一次更新人
     */
    private String lastUpdator;
    /**
     * 最近一次更新时间
     */
    private Date lastUpdateTime;
    /**
     * 最近一次更新IP
     */
    private String lastUpdateIp;
}

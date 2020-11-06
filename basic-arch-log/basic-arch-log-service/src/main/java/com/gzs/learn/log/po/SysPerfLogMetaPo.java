package com.gzs.learn.log.po;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysPerfLogMetaPo implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 
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
     * 类名
     */
    private String clazz;

    /**
     * 方法名
     */
    private String method;

    /**
     * 客户端机器ip
     */
    private String operatorIp;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date timestamp;
}
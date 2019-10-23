package com.gzs.learn.log.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sys_perf_log_meta")
public class SysPerfLogMetaPo implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @Id
    @GeneratedValue(generator = "JDBC")
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
    @Column(name = "operator_ip")
    private String operatorIp;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    private Date timestamp;
}
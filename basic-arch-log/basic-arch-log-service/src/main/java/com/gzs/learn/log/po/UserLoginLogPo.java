package com.gzs.learn.log.po;

import java.io.Serializable;
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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user_login_log")
public class UserLoginLogPo implements Serializable {
    /**
    * 
    */
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 日志名
     */
    private String logname;

    /**
     * 用户id
     */
    private Long userid;

    /**
     * 成功标识
     */
    private String succeed;

    /**
     * 消息
     */
    private String message;

    /**
     * 操作ip
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

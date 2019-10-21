package com.gzs.learn.log.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
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
@Table(name = "user_operation_log")
public class UserOperationLogPo implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    private String logtype;
    private String logname;
    @Column(name = "operator_ip")
    private String operatorIp;
    private Long userid;
    private String classname;
    private String method;
    private String succeed;
    private String message;
    @Column(name = "create_time")
    private Date createTime;
    private Date timestamp;
}

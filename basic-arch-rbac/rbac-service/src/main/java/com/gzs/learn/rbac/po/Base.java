package com.gzs.learn.rbac.po;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Base implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "JDBC")
    protected Long id;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    protected Date createtime;
    /**
     * 更新时间
     */
    protected Date timestamp;
}

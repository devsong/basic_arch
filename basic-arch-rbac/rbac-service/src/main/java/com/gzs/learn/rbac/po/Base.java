package com.gzs.learn.rbac.po;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

import java.io.Serializable;

@Data
public class Base implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "JDBC")
    protected Long id;
}

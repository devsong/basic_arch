package com.gzs.learn.rbac.po;

import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Table(name = "test")
@Data
@EqualsAndHashCode(callSuper = false)
public class Test extends Base {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String name;
}

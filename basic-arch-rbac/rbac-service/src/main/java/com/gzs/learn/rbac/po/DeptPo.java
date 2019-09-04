package com.gzs.learn.rbac.po;

import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 部门表
 * </p>
 */
@Table(name = "sys_dept")
@Data
@EqualsAndHashCode(callSuper = false)
public class DeptPo extends Base {
    private static final long serialVersionUID = 1L;
    /**
     * 排序
     */
    private Integer num;
    /**
     * 父部门id
     */
    private Integer pid;
    /**
     * 父级ids
     */
    private String pids;
    /**
     * 简称
     */
    private String simplename;
    /**
     * 全称
     */
    private String fullname;
    /**
     * 提示
     */
    private String tips;
    /**
     * 版本（乐观锁保留字段）
     */
    private Integer version;
}

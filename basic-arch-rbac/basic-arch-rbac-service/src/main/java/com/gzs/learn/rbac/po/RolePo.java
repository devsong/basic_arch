package com.gzs.learn.rbac.po;

import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 角色表
 * </p>
 */
@Table(name = "sys_role")
@Data
@EqualsAndHashCode(callSuper = false)
public class RolePo extends Base {
    private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    private Integer num;
    /**
     * 父角色id
     */
    private Long pid;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 部门名称
     */
    private Long deptid;
    /**
     * 提示
     */
    private String tips;
    /**
     * 保留字段(暂时没用）
     */
    private Integer version;
}

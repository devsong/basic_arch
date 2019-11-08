package com.gzs.learn.rbac.po;

import javax.persistence.Column;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 角色和菜单关联表
 * </p>
 */
@Table(name = "sys_relation")
@Data
@EqualsAndHashCode(callSuper = false)
public class RelationPo extends Base {
    private static final long serialVersionUID = 1L;
    /**
     * 菜单id
     */
    @Column(name = "menu_id")
    private Long menuid;
    /**
     * 角色id
     */
    @Column(name = "role_id")
    private Long roleid;
}

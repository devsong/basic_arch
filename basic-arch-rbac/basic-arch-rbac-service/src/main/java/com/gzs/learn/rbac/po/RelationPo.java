package com.gzs.learn.rbac.po;

import java.util.Date;

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
    private Long menuid;
    /**
     * 角色id
     */
    private Long roleid;

    @Column(name = "create_time")
    private Date createTime;
    
    @Column(name = "_timestamp")
    private Date timestamp;
}

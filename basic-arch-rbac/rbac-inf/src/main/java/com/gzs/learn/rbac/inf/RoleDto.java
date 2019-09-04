package com.gzs.learn.rbac.inf;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RoleDto extends Base {
    private static final long serialVersionUID = 1L;
    /**
     * 序号
     */
    private Integer num;
    /**
     * 父角色id
     */
    private Integer pid;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 部门名称
     */
    private Integer deptid;
    /**
     * 提示
     */
    private String tips;
}

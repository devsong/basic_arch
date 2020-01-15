package com.gzs.learn.rbac.inf;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private Long pid;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 部门名称
     */
    private Long deptId;
    /**
     * 提示
     */
    private String tips;
}

package com.gzs.learn.rbac.inf;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RelationDto extends Base {
    private static final long serialVersionUID = 1L;

    /**
     * 菜单id
     */
    private Long menuid;
    /**
     * 角色id
     */
    private Long roleid;
}

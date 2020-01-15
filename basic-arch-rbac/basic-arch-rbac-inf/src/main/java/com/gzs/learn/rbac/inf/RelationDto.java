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
public class RelationDto extends Base {
    private static final long serialVersionUID = 1L;

    /**
     * 菜单id
     */
    private Long menuId;
    /**
     * 角色id
     */
    private Long roleId;
}

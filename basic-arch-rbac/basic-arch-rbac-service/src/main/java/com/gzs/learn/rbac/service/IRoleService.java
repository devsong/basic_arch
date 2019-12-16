package com.gzs.learn.rbac.service;

import java.util.List;
import java.util.Set;

import com.gzs.learn.rbac.inf.RoleDto;
import com.gzs.learn.rbac.inf.ZTreeNode;

public interface IRoleService {
    RoleDto getRole(Long roleId);

    List<RoleDto> searchRoles(String roleName);

    List<ZTreeNode> roleTreeListByRoleIds(Set<Long> roleIds);

    List<Long> getRoleByUserId(Long userId);

    boolean insertRole(RoleDto role);

    boolean updateRole(RoleDto role);

    boolean delRole(Long roleId);

    boolean setAuthority(Long roleId, String menuIds);
}

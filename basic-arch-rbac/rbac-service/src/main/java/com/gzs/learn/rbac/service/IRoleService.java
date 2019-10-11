package com.gzs.learn.rbac.service;

import java.util.List;

import com.gzs.learn.rbac.inf.RoleDto;
import com.gzs.learn.rbac.inf.ZTreeNode;

public interface IRoleService {
    RoleDto getRole(Long roleId);

    List<RoleDto> searchRoles(String roleName);

    List<ZTreeNode> roleTreeList();

    List<ZTreeNode> roleTreeListByRoleId(String[] roleId);

    boolean insertRole(RoleDto role);

    boolean updateRole(RoleDto role);

    boolean delRole(Long roleId);

    boolean setAuthority(Long roleId, String menuIds);
}

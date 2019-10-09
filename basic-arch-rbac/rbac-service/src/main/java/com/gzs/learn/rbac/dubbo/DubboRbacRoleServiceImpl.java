package com.gzs.learn.rbac.dubbo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gzs.learn.rbac.inf.RoleDto;
import com.gzs.learn.rbac.inf.ZTreeNode;
import com.gzs.learn.rbac.service.IRoleService;

@Component("dubboRbacRoleService")
public class DubboRbacRoleServiceImpl implements DubboRbacRoleService {

    @Autowired
    private IRoleService roleService;

    @Override
    public List<ZTreeNode> roleTreeList() {
        return roleService.roleTreeList();
    }

    @Override
    public List<ZTreeNode> roleTreeListByRoleId(String[] roleId) {
        return roleService.roleTreeListByRoleId(roleId);
    }

    @Override
    public List<RoleDto> searchRoles(String roleName) {
        return roleService.searchRoles(roleName);
    }

    @Override
    public boolean insertRole(RoleDto role) {
        return roleService.insertRole(role);
    }

    @Override
    public boolean updateRole(RoleDto role) {
        return roleService.updateRole(role);
    }

    @Override
    public boolean delRole(Long roleId) {
        return roleService.delRole(roleId);
    }

    @Override
    public RoleDto getRole(Long role) {
        return roleService.getRole(role);
    }
}

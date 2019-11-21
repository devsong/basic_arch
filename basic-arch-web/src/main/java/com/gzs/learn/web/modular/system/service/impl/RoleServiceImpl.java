package com.gzs.learn.web.modular.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gzs.learn.rbac.dubbo.DubboRbacRoleService;
import com.gzs.learn.web.modular.system.service.IRoleService;

@Component
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private DubboRbacRoleService dubboRbacRoleService;

    @Override
    public void setAuthority(Long roleId, String menuIds) {
        // 删除该角色所有的权限
        dubboRbacRoleService.setAuthority(roleId, menuIds);
    }

    @Override
    public void delRoleById(Long roleId) {
        this.dubboRbacRoleService.delRole(roleId);
    }
}

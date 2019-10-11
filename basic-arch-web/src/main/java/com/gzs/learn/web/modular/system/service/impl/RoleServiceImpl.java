package com.gzs.learn.web.modular.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gzs.learn.rbac.dubbo.DubboRbacRoleService;
import com.gzs.learn.web.common.annotion.DataSource;
import com.gzs.learn.web.common.constant.DSEnum;
import com.gzs.learn.web.modular.system.service.IRoleService;

@Service
@DataSource(DSEnum.DATA_SOURCE_GUNS)
@Transactional()
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

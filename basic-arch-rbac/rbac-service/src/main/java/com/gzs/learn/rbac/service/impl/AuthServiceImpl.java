package com.gzs.learn.rbac.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gzs.learn.rbac.dao.RoleMapper;
import com.gzs.learn.rbac.inf.RoleDto;
import com.gzs.learn.rbac.service.IAuthService;

@Component
public class AuthServiceImpl implements IAuthService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public RoleDto getRole(Long roleId) {
        roleMapper.selectByPrimaryKey(roleId);
        return null;
    }

}

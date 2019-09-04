package com.gzs.learn.rbac.service;

import com.gzs.learn.rbac.inf.RoleDto;

public interface IAuthService {

    /**
     * 查询角色信息
     * @param roleId
     * @return
     */
    RoleDto getRole(Long roleId);
}

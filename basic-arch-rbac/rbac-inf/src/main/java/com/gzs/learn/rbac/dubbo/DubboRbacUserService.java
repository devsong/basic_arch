package com.gzs.learn.rbac.dubbo;

import com.gzs.learn.rbac.inf.DeptDto;
import com.gzs.learn.rbac.inf.RoleDto;
import com.gzs.learn.rbac.inf.UserDto;

/**
 * 基础用户权限
 * @author guanzhisong
 *
 */
public interface DubboRbacUserService {
    /**
     * 查询用户信息
     * @param account
     * @return
     */
    UserDto getUserByAccount(String account);

    /**
     * 获取用户信息
     * @param id
     * @return
     */
    UserDto getUserById(Long userId);

    /**
     * 查询角色信息
     * @param roleId
     * @return
     */
    RoleDto getRole(Integer roleId);

    /**
     * 获取部门信息
     * @param deptId
     * @return
     */
    DeptDto getDept(Integer deptId);
}

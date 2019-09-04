package com.gzs.learn.rbac.service;

import com.gzs.learn.rbac.inf.DeptDto;
import com.gzs.learn.rbac.inf.RoleDto;
import com.gzs.learn.rbac.inf.UserDto;

public interface IUserService {

    UserDto getUser(String account);

    UserDto getUser(Long id);

    RoleDto getRole(Integer roleId);

    DeptDto getDept(Integer deptId);

}

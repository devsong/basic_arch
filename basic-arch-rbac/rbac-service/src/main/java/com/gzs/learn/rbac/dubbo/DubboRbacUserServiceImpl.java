package com.gzs.learn.rbac.dubbo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gzs.learn.rbac.inf.DeptDto;
import com.gzs.learn.rbac.inf.RoleDto;
import com.gzs.learn.rbac.inf.UserDto;
import com.gzs.learn.rbac.service.IUserService;

@Component("dubboRbacUserService")
public class DubboRbacUserServiceImpl implements DubboRbacUserService {
    @Autowired
    private IUserService userService;

    @Override
    public UserDto getUserByAccount(String account) {
        return userService.getUser(account);
    }

    @Override
    public UserDto getUserById(Long id) {
        UserDto userDto = userService.getUser(id);
        return userDto;
    }

    @Override
    public RoleDto getRole(Integer roleId) {
        RoleDto roleDto = userService.getRole(roleId);
        return roleDto;
    }

    @Override
    public DeptDto getDept(Integer deptId) {
        DeptDto deptDto = userService.getDept(deptId);
        return deptDto;
    }
}

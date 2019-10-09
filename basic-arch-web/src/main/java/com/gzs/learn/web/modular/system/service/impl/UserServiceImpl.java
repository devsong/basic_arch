package com.gzs.learn.web.modular.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gzs.learn.rbac.dubbo.DubboRbacMenuService;
import com.gzs.learn.rbac.dubbo.DubboRbacUserService;
import com.gzs.learn.rbac.inf.DataScope;
import com.gzs.learn.rbac.inf.MenuNodeDto;
import com.gzs.learn.rbac.inf.UserDto;
import com.gzs.learn.rbac.inf.UserSearchDto;
import com.gzs.learn.web.modular.system.service.IUserService;

@Component
public class UserServiceImpl implements IUserService {
    @Autowired
    private DubboRbacUserService dubboRbacUserService;

    @Autowired
    private DubboRbacMenuService dubboRbacMenuService;

    @Override
    public List<UserDto> selectUsers(DataScope dataScope, UserSearchDto userSearchDto) {
        return dubboRbacUserService.search(dataScope, userSearchDto);
    }

    @Override
    public UserDto selectByPrimaryKey(Long userId) {
        return dubboRbacUserService.getUserById(userId);
    }

    @Override
    public boolean updateByPrimaryKey(UserDto user) {
        return dubboRbacUserService.updateUser(user) == 1;
    }

    @Override
    public UserDto getByAccount(String account) {
        return dubboRbacUserService.getUserByAccount(account);
    }

    @Override
    public boolean insert(UserDto user) {
        return dubboRbacUserService.insertUser(user) == 1;
    }

    @Override
    public boolean updateByPrimaryKeySelective(UserDto user) {
        return dubboRbacUserService.updateUser(user) == 1;
    }

    @Override
    public boolean setStatus(Long userId, int status) {
        return dubboRbacUserService.setStatus(userId, status);
    }

    @Override
    public boolean setRoles(Long userId, String roleIds) {
        return dubboRbacUserService.setRoles(userId, roleIds);
    }

    @Override
    public List<MenuNodeDto> getMenusByRoleIds(List<Long> roleList) {
        return dubboRbacMenuService.getMenuIdsByRoleIds(roleList);
    }

}

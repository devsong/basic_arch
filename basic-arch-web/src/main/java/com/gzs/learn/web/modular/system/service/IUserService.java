package com.gzs.learn.web.modular.system.service;

import java.util.List;

import com.gzs.learn.rbac.inf.DataScope;
import com.gzs.learn.rbac.inf.MenuNodeDto;
import com.gzs.learn.rbac.inf.UserDto;
import com.gzs.learn.rbac.inf.UserSearchDto;

public interface IUserService {
    UserDto selectByPrimaryKey(Long userId);

    boolean updateByPrimaryKey(UserDto user);

    UserDto getByAccount(String account);

    boolean insert(UserDto createUser);

    boolean updateByPrimaryKeySelective(UserDto createUser);

    boolean setStatus(Long userId, int code);

    boolean setRoles(Long userId, String roleIds);

    List<MenuNodeDto> getMenusByRoleIds(List<Long> roleList);

    List<UserDto> selectUsers(DataScope dataScope, UserSearchDto userSearchDto);
}

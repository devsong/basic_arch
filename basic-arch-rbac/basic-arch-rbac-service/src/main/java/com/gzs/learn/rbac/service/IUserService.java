package com.gzs.learn.rbac.service;

import com.gzs.learn.inf.PageResponseDto;
import com.gzs.learn.rbac.inf.DataScope;
import com.gzs.learn.rbac.inf.UserDto;
import com.gzs.learn.rbac.inf.UserSearchDto;
import com.gzs.learn.rbac.po.UserPo;

public interface IUserService {

    UserDto getUser(String account);

    UserDto getUser(Long id);

    PageResponseDto<UserDto> selectUsers(DataScope dataScope, UserSearchDto searchDto);

    int insert(UserPo userPo);

    int update(UserPo userPo);

    boolean setStatus(Long userId, int status);

    boolean setRoles(Long userId, String roleIds);
}

package com.gzs.learn.rbac.dubbo;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gzs.learn.inf.PageResponseDto;
import com.gzs.learn.rbac.inf.DataScope;
import com.gzs.learn.rbac.inf.UserDto;
import com.gzs.learn.rbac.inf.UserSearchDto;
import com.gzs.learn.rbac.po.UserPo;
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
    public PageResponseDto<UserDto> search(DataScope dataScope, UserSearchDto searchDto) {
        return userService.selectUsers(dataScope, searchDto);
    }

    @Override
    public int insertUser(UserDto user) {
        UserPo userPo = new UserPo();
        BeanUtils.copyProperties(user, userPo);
        return userService.insert(userPo);
    }

    @Override
    public int updateUser(UserDto user) {
        UserPo userPo = new UserPo();
        BeanUtils.copyProperties(user, userPo);
        return userService.update(userPo);
    }

    @Override
    public boolean setStatus(Long userId, int status) {
        return userService.setStatus(userId, status);
    }

    @Override
    public boolean setRoles(Long userId, String roleIds) {
        return userService.setRoles(userId, roleIds);
    }
}

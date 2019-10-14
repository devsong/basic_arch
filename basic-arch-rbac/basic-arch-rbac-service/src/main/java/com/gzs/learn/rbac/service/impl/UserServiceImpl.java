package com.gzs.learn.rbac.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gzs.learn.common.util.BeanUtil;
import com.gzs.learn.rbac.RbacConsts;
import com.gzs.learn.rbac.dao.UserMapper;
import com.gzs.learn.rbac.inf.DataScope;
import com.gzs.learn.rbac.inf.UserDto;
import com.gzs.learn.rbac.inf.UserSearchDto;
import com.gzs.learn.rbac.po.UserPo;
import com.gzs.learn.rbac.service.IUserService;

@Component
@Transactional
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDto getUser(String account) {
        Integer[] invalidStatus = new Integer[] { RbacConsts.DATA_STATUS_ENABLED, RbacConsts.DATA_STATUS_DISABLED };
        UserPo userPo = userMapper.getByAccount(account, invalidStatus);
        UserDto userDto = new UserDto();
        BeanUtil.copyProperties(userPo, userDto);
        return userDto;
    }

    @Override
    public UserDto getUser(Long id) {
        UserPo userPo = userMapper.selectByPrimaryKey(id);
        UserDto userDto = new UserDto();
        BeanUtil.copyProperties(userPo, userDto);
        return userDto;
    }

    @Override
    public List<UserDto> selectUsers(DataScope dataScope, UserSearchDto searchDto) {
        List<UserPo> users = userMapper.selectUsers(dataScope, searchDto);
        List<UserDto> userDtos = BeanUtil.copyList(users, UserDto.class);
        return userDtos;
    }

    @Override
    public int insert(UserPo userPo) {
        return userMapper.insertSelective(userPo);
    }

    @Override
    public int update(UserPo userPo) {
        return userMapper.updateByPrimaryKeySelective(userPo);
    }

    @Override
    public boolean setStatus(Long userId, int status) {
        return userMapper.setStatus(userId, status) == 1;
    }

    @Override
    public boolean setRoles(Long userId, String roleIds) {
        return userMapper.setRoles(userId, roleIds) == 1;
    }

}

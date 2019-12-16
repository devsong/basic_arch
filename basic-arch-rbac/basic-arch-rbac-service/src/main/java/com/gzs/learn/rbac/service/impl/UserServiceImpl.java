package com.gzs.learn.rbac.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Sets;
import com.gzs.learn.common.util.BeanUtil;
import com.gzs.learn.inf.PageResponseDto;
import com.gzs.learn.inf.PageResponseDto.PageResponse;
import com.gzs.learn.inf.PageResponseDto.PageResponseDtoBuilder;
import com.gzs.learn.rbac.RbacConsts;
import com.gzs.learn.rbac.dao.UserMapper;
import com.gzs.learn.rbac.dao.UserRoleMapper;
import com.gzs.learn.rbac.exception.RbacException;
import com.gzs.learn.rbac.inf.DataScope;
import com.gzs.learn.rbac.inf.UserDto;
import com.gzs.learn.rbac.inf.UserSearchDto;
import com.gzs.learn.rbac.po.UserPo;
import com.gzs.learn.rbac.po.UserRolePo;
import com.gzs.learn.rbac.service.IUserService;

@Component
@Transactional
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public UserDto getUser(String account) {
        Integer[] invalidStatus = new Integer[] { RbacConsts.DATA_STATUS_ENABLED, RbacConsts.DATA_STATUS_DISABLED };
        UserPo userPo = userMapper.getByAccount(account, invalidStatus);
        if (userPo == null) {
            throw new RbacException("can not find account:" + account);
        }
        long userId = userPo.getId();
        Set<Long> roleIds = Sets.newHashSet(userRoleMapper.selectRoleIdsByUserId(userId));
        if (StringUtils.isNotBlank(userPo.getRoleid())) {
            String[] roleArray = userPo.getRoleid().split(",");
            for (String roleId : roleArray) {
                roleIds.add(Long.parseLong(roleId));
            }
        }
        UserDto userDto = new UserDto();
        BeanUtil.copyProperties(userPo, userDto);
        if (!CollectionUtils.isEmpty(roleIds)) {
            userDto.setRoleIds(roleIds);
            userDto.setRoleid(roleIds.stream().map(r -> r.toString()).collect(Collectors.joining(",")));
        }
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
    public PageResponseDto<UserDto> selectUsers(DataScope dataScope, UserSearchDto searchDto) {
        PageResponseDtoBuilder<UserDto> builder = PageResponseDto.builder();
        PageInfo<UserPo> users = PageHelper.startPage(searchDto.getPage(), searchDto.getPageSize(), true).doSelectPageInfo(() -> {
            userMapper.selectUsers(dataScope, searchDto);
        });
        PageResponse page = PageResponse.builder().page(searchDto.getPage()).pageSize(searchDto.getPageSize()).total((int) users.getTotal())
                .build();

        List<UserDto> userDtos = BeanUtil.copyList(users.getList(), UserDto.class);
        PageResponseDto<UserDto> result = builder.code(0).msg("success").data(userDtos).page(page).build();
        return result;
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
        userMapper.setRoles(userId, roleIds);
        String[] roleIdArray = roleIds.split(",");
        UserRolePo deletePo = new UserRolePo();
        deletePo.setUserid(userId);
        userRoleMapper.delete(deletePo);
        for (String roleId : roleIdArray) {
            UserRolePo po = new UserRolePo();
            po.setUserid(userId);
            po.setRoleid(Long.parseLong(roleId));
            po.setCreatetime(new Date());
            userRoleMapper.insertSelective(po);
        }
        return true;
    }

}

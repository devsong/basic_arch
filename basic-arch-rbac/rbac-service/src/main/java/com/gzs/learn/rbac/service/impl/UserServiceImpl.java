package com.gzs.learn.rbac.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gzs.learn.rbac.RbacConsts;
import com.gzs.learn.rbac.dao.DeptMapper;
import com.gzs.learn.rbac.dao.RoleMapper;
import com.gzs.learn.rbac.dao.UserMapper;
import com.gzs.learn.rbac.inf.DeptDto;
import com.gzs.learn.rbac.inf.RoleDto;
import com.gzs.learn.rbac.inf.UserDto;
import com.gzs.learn.rbac.po.DeptPo;
import com.gzs.learn.rbac.po.RolePo;
import com.gzs.learn.rbac.po.UserPo;
import com.gzs.learn.rbac.service.IUserService;

@Component
@Transactional
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private DeptMapper deptMapper;

    @Override
    public UserDto getUser(String account) {
        Integer[] invalidStatus = new Integer[] { RbacConsts.DATA_STATUS_ENABLED, RbacConsts.DATA_STATUS_DISABLED };
        UserPo userPo = userMapper.getByAccount(account, invalidStatus);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userPo, userDto);
        return userDto;
    }

    @Override
    public UserDto getUser(Long id) {
        UserPo userPo = userMapper.selectByPrimaryKey(id);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userPo, userDto);
        return userDto;
    }

    @Override
    public RoleDto getRole(Integer roleId) {
        RolePo rolePo = roleMapper.selectByPrimaryKey(roleId.longValue());
        RoleDto roleDto = new RoleDto();
        BeanUtils.copyProperties(rolePo, roleDto);
        return roleDto;
    }

    @Override
    public DeptDto getDept(Integer deptId) {
        DeptPo deptPo = deptMapper.selectByPrimaryKey(deptId.longValue());
        DeptDto deptDto = new DeptDto();
        BeanUtils.copyProperties(deptPo, deptDto);
        return deptDto;
    }

}

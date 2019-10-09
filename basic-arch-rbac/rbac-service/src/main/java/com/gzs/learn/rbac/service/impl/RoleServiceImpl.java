package com.gzs.learn.rbac.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gzs.learn.common.util.BeanUtil;
import com.gzs.learn.rbac.dao.RoleMapper;
import com.gzs.learn.rbac.inf.RoleDto;
import com.gzs.learn.rbac.inf.ZTreeNode;
import com.gzs.learn.rbac.po.RolePo;
import com.gzs.learn.rbac.service.IRoleService;

@Component
@Transactional
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<ZTreeNode> roleTreeList() {
        return roleMapper.roleTreeList();
    }

    @Override
    public List<ZTreeNode> roleTreeListByRoleId(String[] roleId) {
        return roleMapper.roleTreeListByRoleId(roleId);
    }

    @Override
    public List<RoleDto> searchRoles(String roleName) {
        List<RolePo> roles = roleMapper.searchRoles(roleName);
        List<RoleDto> roleDtos = BeanUtil.copyList(roles, RoleDto.class);
        return roleDtos;
    }

    @Override
    public boolean insertRole(RoleDto role) {
        RolePo rolePo = new RolePo();
        BeanUtil.copyProperties(role, rolePo);
        return roleMapper.insertSelective(rolePo) == 1;
    }

    @Override
    public boolean updateRole(RoleDto role) {
        RolePo rolePo = new RolePo();
        BeanUtil.copyProperties(role, rolePo);
        return roleMapper.updateByPrimaryKeySelective(rolePo) == 1;
    }

    @Override
    public RoleDto getRole(Long roleId) {
        RolePo rolePo = roleMapper.selectByPrimaryKey(roleId);
        if (rolePo == null) {
            return null;
        }
        RoleDto roleDto = new RoleDto();
        BeanUtil.copyProperties(rolePo, roleDto);
        return roleDto;
    }

    @Override
    public boolean delRole(Long roleId) {
        return roleMapper.deleteByPrimaryKey(roleId) == 1;
    }
}

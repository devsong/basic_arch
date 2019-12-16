package com.gzs.learn.rbac.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gzs.learn.common.util.BeanUtil;
import com.gzs.learn.rbac.dao.RelationMapper;
import com.gzs.learn.rbac.dao.RoleMapper;
import com.gzs.learn.rbac.dao.UserRoleMapper;
import com.gzs.learn.rbac.inf.RoleDto;
import com.gzs.learn.rbac.inf.ZTreeNode;
import com.gzs.learn.rbac.po.RelationPo;
import com.gzs.learn.rbac.po.RolePo;
import com.gzs.learn.rbac.po.UserRolePo;
import com.gzs.learn.rbac.service.IRoleService;

import tk.mybatis.mapper.entity.Example;

@Component
@Transactional
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RelationMapper relationMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public List<ZTreeNode> roleTreeListByRoleIds(Set<Long> roleIds) {
        return roleMapper.roleTreeListByRoleIds(roleIds);
    }

    @Override
    public List<RoleDto> searchRoles(String roleName) {
        List<RolePo> roles = roleMapper.searchRoles(roleName);
        List<RoleDto> roleDtos = BeanUtil.copyList(roles, RoleDto.class);
        return roleDtos;
    }

    @Override
    public List<Long> getRoleByUserId(Long userId) {
        return userRoleMapper.selectRoleIdsByUserId(userId);
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
        // 删除角色
        roleMapper.deleteByPrimaryKey(roleId);
        // 角色菜单表
        Example delExample = new Example(RelationPo.class);
        delExample.createCriteria().andEqualTo("roleid", roleId);
        relationMapper.deleteByExample(delExample);
        // 角色用户关系表
        delExample = new Example(UserRolePo.class);
        delExample.createCriteria().andEqualTo("roleid", roleId);
        userRoleMapper.deleteByExample(delExample);
        return true;
    }

    @Override
    public boolean setAuthority(Long roleId, String menuIds) {
        List<Long> menus = Stream.of(menuIds.split(",")).map(s -> Long.parseLong(s)).collect(Collectors.toList());
        // 添加新的权限
        for (Long id : menus) {
            RelationPo relation = new RelationPo();
            relation.setRoleid(roleId);
            relation.setMenuid(id);
            relationMapper.insertSelective(relation);
        }
        return true;
    }
}

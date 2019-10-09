package com.gzs.learn.rbac.dubbo;

import java.util.List;

import com.gzs.learn.rbac.inf.RoleDto;
import com.gzs.learn.rbac.inf.ZTreeNode;

public interface DubboRbacRoleService {
    /*
     * 获取角色列表树
     *
     * @return
     */
    List<ZTreeNode> roleTreeList();

    /**
    * 获取角色列表树
    *
    * @return
    */
    List<ZTreeNode> roleTreeListByRoleId(String[] roleId);

    /**
    * 查询角色列表
    * @param roleName
    * @return
    */
    List<RoleDto> searchRoles(String roleName);

    /**
    * 添加角色
    * @param role
    * @return
    */
    boolean insertRole(RoleDto role);

    /**
    * 更新角色信息
    * @param role
    * @return
    */
    boolean updateRole(RoleDto role);

    /**
     * 删除角色
     * @param roleId
     * @return
     */
    boolean delRole(Long roleId);

    RoleDto getRole(Long role);

}

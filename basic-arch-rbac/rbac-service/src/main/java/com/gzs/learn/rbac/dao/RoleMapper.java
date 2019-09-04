package com.gzs.learn.rbac.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import com.gzs.learn.rbac.inf.ZTreeNode;
import com.gzs.learn.rbac.po.RolePo;

import tk.mybatis.mapper.common.Mapper;

public interface RoleMapper extends Mapper<RolePo> {
    /**
     * 根据条件查询角色列表
     *
     * @return
     */
    List<RolePo> selectRoles(@Param("condition") String condition);

    /**
     * 删除某个角色的所有权限
     *
     * @param roleId 角色id
     * @return
     */
    @Delete("delete from sys_relation where roleid = #{roleId}")
    int deleteRolesById(@Param("roleId") Integer roleId);

    /**
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
}
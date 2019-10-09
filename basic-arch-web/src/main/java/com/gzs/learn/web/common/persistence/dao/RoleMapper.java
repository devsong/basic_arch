package com.gzs.learn.web.common.persistence.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.gzs.learn.web.common.persistence.model.Role;

import tk.mybatis.mapper.common.Mapper;

/**
 * <p>
  * 角色表 Mapper 接口
 * </p>
 */
public interface RoleMapper extends Mapper<Role> {

    /**
     * 根据条件查询角色列表
     *
     * @return
     */
    List<Map<String, Object>> selectRoles(@Param("condition") String condition);

    /**
     * 删除某个角色的所有权限
     *
     * @param roleId 角色id
     * @return
     */
    int deleteRolesById(@Param("roleId") Integer roleId);
}
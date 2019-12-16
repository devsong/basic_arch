package com.gzs.learn.rbac.dao;

import java.util.Set;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.gzs.learn.rbac.po.UserRolePo;

import tk.mybatis.mapper.common.Mapper;

public interface UserRoleMapper extends Mapper<UserRolePo> {

    @Select("select role_id from sys_user_role where user_id = #{userId}")
    Set<Long> selectRoleIdsByUserId(@Param("userId") Long userId);

    @Delete("delete sys_user_role where role_id = #{roleId}")
    int deleteUserRoleByRoleId(@Param("roleId") Long roleId);

    @Delete("delete sys_user_role where user_id = #{userId}")
    int deleteUserRoleByUserId(@Param("userId") Long userId);
}
package com.gzs.learn.rbac.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.gzs.learn.rbac.inf.DataScope;
import com.gzs.learn.rbac.inf.UserSearchDto;
import com.gzs.learn.rbac.po.UserPo;

import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<UserPo> {
    /**
     * 修改用户状态
     *
     * @param userId
     * @param status
     * @date 2017年2月12日 下午8:42:31
     */
    @Update("update sys_user set status = #{status} where id = #{userId}")
    int setStatus(@Param("userId") Long userId, @Param("status") int status);

    /**
     * 修改密码
     *
     * @param userId
     * @param pwd
     * @date 2017年2月12日 下午8:54:19
     */
    @Update("update sys_user set password = #{pwd} where id = #{userId}")
    int changePwd(@Param("userId") Long userId, @Param("pwd") String pwd);

    /**
     * 根据条件查询用户列表
     *
     * @return
     * @date 2017年2月12日 下午9:14:34
     */
    List<UserPo> selectUsers(@Param("dataScope") DataScope dataScope, @Param("userSearchDto") UserSearchDto userSearchDto);

    /**
     * 设置用户的角色
     *
     * @return
     * @date 2017年2月13日 下午7:31:30
     */
    @Update("update sys_user set role_id = #{roleIds} where id = #{userId}")
    int setRoles(@Param("userId") Long userId, @Param("roleIds") String roleIds);

    /**
     * 通过账号获取用户
     *
     * @param account
     * @return
     * @date 2017年2月17日 下午11:07:46
     */
    UserPo getByAccount(@Param("account") String account, @Param("status") Integer... status);
}
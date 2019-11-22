package com.gzs.learn.rbac.dubbo;

import com.gzs.learn.inf.PageResponseDto;
import com.gzs.learn.rbac.inf.DataScope;
import com.gzs.learn.rbac.inf.UserDto;
import com.gzs.learn.rbac.inf.UserSearchDto;

/**
 * 基础用户权限
 * @author guanzhisong
 *
 */
public interface DubboRbacUserService {
    /**
     * 查询用户信息
     * @param account
     * @return
     */
    UserDto getUserByAccount(String account);

    /**
     * 获取用户信息
     * @param id
     * @return
     */
    UserDto getUserById(Long userId);

    /**
     * 用户搜索
     * @param dataScope
     * @param searchDto
     * @return
     */
    PageResponseDto<UserDto> search(DataScope dataScope, UserSearchDto searchDto);

    /**
     * 写入用户信息
     * @param user
     * @return
     */
    int insertUser(UserDto user);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    int updateUser(UserDto user);

    /**
     * 设置指定用户id状态字段
     * @param userId
     * @param status
     * @return
     */
    boolean setStatus(Long userId, int status);

    /**
     * 赋予指定用户角色
     * @param userId
     * @param roleIds
     * @return
     */
    boolean setRoles(Long userId, String roleIds);
}

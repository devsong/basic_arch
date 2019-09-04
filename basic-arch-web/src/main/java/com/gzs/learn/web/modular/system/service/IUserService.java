package com.gzs.learn.web.modular.system.service;

import java.util.List;

import com.gzs.learn.web.common.node.MenuNode;
import com.gzs.learn.web.common.persistence.model.User;
import com.gzs.learn.web.core.datascope.DataScope;
import com.gzs.learn.web.modular.system.dto.UserSearchDto;

public interface IUserService {
    List<User> selectUsers(DataScope dataScope, UserSearchDto userSearchDto);

    User selectByPrimaryKey(Long userId);

    boolean updateByPrimaryKey(User user);

    User getByAccount(String account);

    boolean insert(User createUser);

    boolean updateByPrimaryKeySelective(User createUser);

    boolean setStatus(Long userId, int code);

    boolean setRoles(Long userId, String roleIds);

    List<MenuNode> getMenusByRoleIds(List<Integer> roleList);
}

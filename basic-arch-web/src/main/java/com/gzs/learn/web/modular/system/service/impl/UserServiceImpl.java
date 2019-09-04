package com.gzs.learn.web.modular.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gzs.learn.web.common.annotion.DataSource;
import com.gzs.learn.web.common.constant.DSEnum;
import com.gzs.learn.web.common.node.MenuNode;
import com.gzs.learn.web.common.persistence.dao.MenuMapper;
import com.gzs.learn.web.common.persistence.dao.UserMapper;
import com.gzs.learn.web.common.persistence.model.User;
import com.gzs.learn.web.core.datascope.DataScope;
import com.gzs.learn.web.modular.system.dto.UserSearchDto;
import com.gzs.learn.web.modular.system.service.IUserService;

import tk.mybatis.mapper.entity.Example;

@Component
@DataSource(DSEnum.DATA_SOURCE_GUNS)
@Transactional
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<User> selectUsers(DataScope dataScope, UserSearchDto userSearchDto) {
        return userMapper.selectUsers(dataScope, userSearchDto);
    }

    @Override
    public User selectByPrimaryKey(Long userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public boolean updateByPrimaryKey(User user) {
        return userMapper.updateByPrimaryKey(user) == 1;
    }

    @Override
    public User getByAccount(String account) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("account", account);
        return userMapper.selectOneByExample(example);
    }

    @Override
    public boolean insert(User user) {
        return userMapper.insertSelective(user) == 1;
    }

    @Override
    public boolean updateByPrimaryKeySelective(User user) {
        return userMapper.updateByPrimaryKeySelective(user) == 1;
    }

    @Override
    public boolean setStatus(Long userId, int status) {
        userMapper.setStatus(userId, status);
        return true;
    }

    @Override
    public boolean setRoles(Long userId, String roleIds) {
        userMapper.setRoles(userId, roleIds);
        return true;
    }

    @Override
    public List<MenuNode> getMenusByRoleIds(List<Integer> roleList) {
        return menuMapper.getMenusByRoleIds(roleList);
    }

}

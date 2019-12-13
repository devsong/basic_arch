package com.gzs.learn.web.core.shiro.factory;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gzs.learn.rbac.dubbo.DubboRbacMenuService;
import com.gzs.learn.rbac.dubbo.DubboRbacUserService;
import com.gzs.learn.rbac.inf.UserDto;
import com.gzs.learn.web.common.constant.enums.ManagerStatus;
import com.gzs.learn.web.common.constant.factory.ConstantFactory;
import com.gzs.learn.web.core.shiro.ShiroUser;
import com.gzs.learn.web.core.util.Convert;
import com.gzs.learn.web.core.util.SpringContextHolder;

@Component
public class ShiroFactroy implements IShiro {
    @Autowired
    private DubboRbacMenuService dubboRbacMenuService;

    @Autowired
    private DubboRbacUserService dubboRbacUserService;

    public static IShiro me() {
        return SpringContextHolder.getBean(IShiro.class);
    }

    @Override
    public UserDto user(String account) {
        UserDto userDto = dubboRbacUserService.getUserByAccount(account);

        // 账号不存在
        if (userDto == null) {
            throw new CredentialsException();
        }
        // 账号被冻结
        if (userDto.getStatus() != ManagerStatus.OK.getCode()) {
            throw new LockedAccountException();
        }
        return userDto;
    }

    @Override
    public ShiroUser shiroUser(UserDto user) {
        ShiroUser shiroUser = new ShiroUser();

        shiroUser.setId(user.getId()); // 账号id
        shiroUser.setAccount(user.getAccount());// 账号
        shiroUser.setDeptId(user.getDeptid()); // 部门id
        shiroUser.setDeptName(ConstantFactory.me().getDeptName(user.getDeptid()));// 部门名称
        shiroUser.setName(user.getName()); // 用户名称

        Long[] roleArray = Convert.toLongArray(user.getRoleid());// 角色集合
        List<Long> roleList = new ArrayList<Long>();
        List<String> roleNameList = new ArrayList<String>();
        for (Long roleId : roleArray) {
            roleList.add(roleId);
            roleNameList.add(ConstantFactory.me().getSingleRoleName(roleId));
        }
        shiroUser.setRoleList(roleList);
        shiroUser.setRoleNames(roleNameList);

        return shiroUser;
    }

    @Override
    public List<String> findPermissionsByRoleId(List<Long> roleId) {
        List<String> resUrls = dubboRbacMenuService.findPermissionsByRoleId(roleId);
        return resUrls;
    }

    @Override
    public String findRoleNameByRoleId(Long roleId) {
        return ConstantFactory.me().getSingleRoleTip(roleId);
    }

    @Override
    public SimpleAuthenticationInfo info(ShiroUser shiroUser, UserDto user, String realmName) {
        String credentials = user.getPassword();
        // 密码加盐处理
        String source = user.getSalt();
        ByteSource credentialsSalt = new Md5Hash(source);
        return new SimpleAuthenticationInfo(shiroUser, credentials, credentialsSalt, realmName);
    }

}

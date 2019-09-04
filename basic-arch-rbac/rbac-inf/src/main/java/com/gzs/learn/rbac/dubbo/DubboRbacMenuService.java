package com.gzs.learn.rbac.dubbo;

import java.util.List;

import com.gzs.learn.rbac.inf.MenuDto;

/**
 * 基础菜单权限
 * @author guanzhisong
 *
 */
public interface DubboRbacMenuService {
    /**
     * 获取角色权限
     * @param roleId
     * @return
     */
    List<String> findPermissionsByRoleId(Integer roleId);

    /**
     * 查询菜单
     * @param menu
     * @return
     */
    MenuDto getMenu(Integer menuId);

    /**
     * 查询菜单
     * @param menu
     * @return
     */
    MenuDto getMenuByCode(String code);

    /**
     * 查询菜单
     * @param menu
     * @return
     */
    List<MenuDto> getMenu(List<Integer> menuIds);
}

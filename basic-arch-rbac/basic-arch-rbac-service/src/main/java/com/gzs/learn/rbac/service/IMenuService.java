package com.gzs.learn.rbac.service;

import java.util.List;

import com.gzs.learn.rbac.inf.MenuDto;
import com.gzs.learn.rbac.inf.MenuNodeDto;
import com.gzs.learn.rbac.inf.ZTreeNode;

public interface IMenuService {

    List<String> findPermissionsByRoleId(Long roleId);

    MenuDto getMenu(Long menuId);

    List<MenuDto> getMenu(List<Long> menuIds);

    MenuDto getMenuByCode(String code);

    List<MenuDto> searchMenu(String menuName, String level);

    boolean updateMenu(MenuDto menu);

    boolean insertMenu(MenuDto menu);

    List<ZTreeNode> menuTreeList();

    boolean delMenuContainSunMenus(Long menuId);

    List<Long> getMenuIdsByRoleId(Long roleId);

    List<ZTreeNode> menuTreeListByMenuIds(List<Long> menuIds);

    List<MenuNodeDto> getMenuIdsByRoleIds(List<Long> roleList);
}

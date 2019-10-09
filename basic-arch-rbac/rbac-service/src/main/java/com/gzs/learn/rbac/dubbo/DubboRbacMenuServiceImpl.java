package com.gzs.learn.rbac.dubbo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gzs.learn.rbac.inf.MenuDto;
import com.gzs.learn.rbac.inf.MenuNodeDto;
import com.gzs.learn.rbac.inf.ZTreeNode;
import com.gzs.learn.rbac.service.IMenuService;

@Component("dubboRbacMenuService")
public class DubboRbacMenuServiceImpl implements DubboRbacMenuService {
    @Autowired
    private IMenuService menuService;

    @Override
    public List<String> findPermissionsByRoleId(Long roleId) {
        List<String> res = menuService.findPermissionsByRoleId(roleId);
        return res;
    }

    @Override
    public MenuDto getMenu(Long menuId) {
        MenuDto menuDto = menuService.getMenu(menuId);
        return menuDto;
    }

    @Override
    public List<MenuDto> getMenu(List<Long> menuIds) {
        List<MenuDto> list = menuService.getMenu(menuIds);
        return list;
    }

    @Override
    public MenuDto getMenuByCode(String code) {
        MenuDto menuDto = menuService.getMenuByCode(code);
        return menuDto;
    }

    @Override
    public boolean updateMenu(MenuDto menu) {
        return menuService.updateMenu(menu);
    }

    @Override
    public List<MenuDto> searchMenus(String menuName, String level) {
        return menuService.searchMenu(menuName, level);
    }

    @Override
    public boolean insertMenu(MenuDto menu) {
        return menuService.insertMenu(menu);
    }

    @Override
    public List<ZTreeNode> menuTreeList() {
        return menuService.menuTreeList();
    }

    @Override
    public boolean delMenuContainSubMenus(Long menuId) {
        return false;
    }

    @Override
    public List<Long> getMenuIdsByRoleId(Long roleId) {
        return null;
    }

    @Override
    public List<ZTreeNode> menuTreeListByMenuIds(List<Long> menuIds) {
        return null;
    }

    @Override
    public List<MenuNodeDto> getMenuIdsByRoleIds(List<Long> roleList) {
        return null;
    }

}

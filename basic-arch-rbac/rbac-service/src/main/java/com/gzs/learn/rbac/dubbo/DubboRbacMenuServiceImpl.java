package com.gzs.learn.rbac.dubbo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gzs.learn.rbac.inf.MenuDto;
import com.gzs.learn.rbac.service.IMenuService;

@Component("dubboRbacMenuService")
public class DubboRbacMenuServiceImpl implements DubboRbacMenuService {

    @Autowired
    private IMenuService menuService;

    @Override
    public List<String> findPermissionsByRoleId(Integer roleId) {
        List<String> res = menuService.findPermissionsByRoleId(roleId);
        return res;
    }

    @Override
    public MenuDto getMenu(Integer menuId) {
        MenuDto menuDto = menuService.getMenu(menuId);
        return menuDto;
    }

    @Override
    public List<MenuDto> getMenu(List<Integer> menuIds) {
        List<MenuDto> list = menuService.getMenu(menuIds);
        return list;
    }

    @Override
    public MenuDto getMenuByCode(String code) {
        MenuDto menuDto = menuService.getMenuByCode(code);
        return menuDto;
    }

}

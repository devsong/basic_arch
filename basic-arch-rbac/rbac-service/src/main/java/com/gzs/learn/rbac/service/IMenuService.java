package com.gzs.learn.rbac.service;

import java.util.List;

import com.gzs.learn.rbac.inf.MenuDto;

public interface IMenuService {

    List<String> findPermissionsByRoleId(Integer roleId);

    MenuDto getMenu(Integer menuId);

    List<MenuDto> getMenu(List<Integer> menuIds);

    MenuDto getMenuByCode(String code);

}

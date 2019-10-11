package com.gzs.learn.web.modular.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gzs.learn.rbac.dubbo.DubboRbacMenuService;
import com.gzs.learn.rbac.inf.MenuNodeDto;
import com.gzs.learn.rbac.inf.ZTreeNode;
import com.gzs.learn.web.modular.system.service.IMenuService;

/**
 * 菜单服务
 */
@Component
public class MenuServiceImpl implements IMenuService {
    @Autowired
    private DubboRbacMenuService dubboRbacMenuService;

    @Override
    public void delMenuContainSubMenus(Long menuId) {
        dubboRbacMenuService.delMenuContainSubMenus(menuId);
    }

    @Override
    public List<Map<String, Object>> selectMenus(String condition, String level) {
        return null;
    }

    @Override
    public List<Long> getMenuIdsByRoleId(Long roleId) {
        return null;
    }

    @Override
    public List<ZTreeNode> menuTreeList() {
        return null;
    }

    @Override
    public List<ZTreeNode> menuTreeListByMenuIds(List<Long> menuIds) {
        return null;
    }

    @Override
    public List<String> getResUrlsByRoleId(Long roleId) {
        return null;
    }

    @Override
    public List<MenuNodeDto> getMenusByRoleIds(List<Long> roleIds) {
        return null;
    }
}

package com.gzs.learn.web.modular.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.gzs.learn.rbac.inf.MenuNodeDto;
import com.gzs.learn.rbac.inf.ZTreeNode;
import com.gzs.learn.web.common.persistence.dao.MenuMapper;
import com.gzs.learn.web.common.persistence.model.Menu;
import com.gzs.learn.web.modular.system.service.IMenuService;

import tk.mybatis.mapper.entity.Example;

/**
 * 菜单服务
 */
@Component
public class MenuServiceImpl implements IMenuService {
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public void delMenu(Long menuId) {
        // 删除菜单
        this.menuMapper.deleteByPrimaryKey(menuId);
        // 删除关联的relation
        this.menuMapper.deleteRelationByMenu(menuId);
    }

    @Override
    public void delMenuContainSubMenus(Long menuId) {
        Menu menu = menuMapper.selectByPrimaryKey(menuId);

        // 删除当前菜单
        delMenu(menuId);

        // 删除所有子菜单
        Example example = new Example(Menu.class);
        example.createCriteria().andLike("pcodes", "%[" + menu.getCode() + "]%");
        List<Menu> menus = menuMapper.selectByExample(example);
        for (Menu temp : menus) {
            delMenu(temp.getId());
        }
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
    public int deleteRelationByMenu(Long menuId) {
        return 0;
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

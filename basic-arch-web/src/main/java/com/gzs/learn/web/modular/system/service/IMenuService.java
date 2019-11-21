package com.gzs.learn.web.modular.system.service;

import java.util.List;
import java.util.Map;

import com.gzs.learn.rbac.inf.MenuNodeDto;
import com.gzs.learn.rbac.inf.ZTreeNode;

/**
 * 菜单服务
 *
 * @author fengshuonan
 * @date 2017-05-05 22:19
 */
public interface IMenuService {
    /**
     * 删除菜单包含所有子菜单
     *
     */
    void delMenuContainSubMenus(Long menuId);

    /**
     * 根据条件查询菜单
     *
     * @return
     */
    List<Map<String, Object>> selectMenus(String condition, String level);

    /**
     * 根据条件查询菜单
     *
     * @return
     */
    List<Long> getMenuIdsByRoleId(Long roleId);

    /**
     * 获取菜单列表树
     *
     * @return
     */
    List<ZTreeNode> menuTreeList();

    /**
     * 获取菜单列表树
     *
     * @return
     */
    List<ZTreeNode> menuTreeListByMenuIds(List<Long> menuIds);

    /**
     * 获取资源url通过角色id
     *
     * @param roleId
     * @return
     */
    List<String> getResUrlsByRoleId(Long roleId);

    /**
     * 根据角色获取菜单
     *
     * @param roleIds
     * @return
     */
    List<MenuNodeDto> getMenusByRoleIds(List<Long> roleIds);
}

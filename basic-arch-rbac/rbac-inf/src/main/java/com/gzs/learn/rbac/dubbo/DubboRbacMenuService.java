package com.gzs.learn.rbac.dubbo;

import java.util.List;

import com.gzs.learn.rbac.inf.MenuDto;
import com.gzs.learn.rbac.inf.MenuNodeDto;
import com.gzs.learn.rbac.inf.ZTreeNode;

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
    List<String> findPermissionsByRoleId(Long roleId);

    /**
     * 查询菜单
     * @param menu
     * @return
     */
    MenuDto getMenu(Long menuId);

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
    List<MenuDto> getMenu(List<Long> menuIds);

    /**
     * 更新菜单信息
     * @param menu
     * @return
     */
    boolean updateMenu(MenuDto menu);

    /**
     * 搜索菜单信息
     * @param menuName
     * @param level
     * @return
     */
    List<MenuDto> searchMenus(String menuName, String level);

    /**
     * 保存菜单信息
     * @param menu
     * @return
     */
    boolean insertMenu(MenuDto menu);

    /**
     * 获取菜单列表
     * @return
     */
    List<ZTreeNode> menuTreeList();

    /**
     * 删除
     * @param menuId
     * @return
     */
    boolean delMenuContainSubMenus(Long menuId);

    /**
     * 获取菜单id
     * @param roleId
     * @return
     */
    List<Long> getMenuIdsByRoleId(Long roleId);

    /**
     * 获取菜单列表
     * @param menuIds
     * @return
     */
    List<ZTreeNode> menuTreeListByMenuIds(List<Long> menuIds);

    List<MenuNodeDto> getMenuIdsByRoleIds(List<Long> roleList);
}

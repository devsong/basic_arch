package com.gzs.learn.rbac.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.gzs.learn.rbac.inf.MenuNodeDto;
import com.gzs.learn.rbac.inf.ZTreeNode;
import com.gzs.learn.rbac.po.MenuPo;

import tk.mybatis.mapper.common.Mapper;

/**
 * <p>
  * 菜单表 Mapper 接口
 * </p>
 */
public interface MenuMapper extends Mapper<MenuPo> {

    /**
     * 根据条件查询菜单
     *
     * @return
     */
    List<MenuPo> selectMenus(@Param("condition") String condition, @Param("level") String level);

    /**
     * 根据条件查询菜单
     *
     * @return
     * @date 2017年2月12日 下午9:14:34
     */
    @Select("select menuid from sys_relation where roleid = #{roleId}")
    List<Integer> getMenuIdsByRoleId(@Param("roleId") Integer roleId);

    /**
     * 获取菜单列表树
     *
     * @return
     * @date 2017年2月19日 下午1:33:51
     */
    List<ZTreeNode> menuTreeList();

    /**
     * 获取菜单列表树
     *
     * @return
     * @date 2017年2月19日 下午1:33:51
     */
    List<ZTreeNode> menuTreeListByMenuIds(List<Integer> menuIds);

    /**
     * 删除menu关联的relation
     *
     * @param menuId
     * @return
     * @date 2017年2月19日 下午4:10:59
     */
    @Delete("delete from sys_relation where menuid = #{menuId}")
    int deleteRelationByMenu(@Param("menuId")Integer menuId);

    /**
     * 获取资源url通过角色id
     *
     * @param roleId
     * @return
     * @date 2017年2月19日 下午7:12:38
     */
    List<String> getResUrlsByRoleId(Integer roleId);

    /**
     * 根据角色获取菜单
     *
     * @param roleIds
     * @return
     * @date 2017年2月19日 下午10:35:40
     */
    List<MenuNodeDto> getMenusByRoleIds(List<Integer> roleIds);
}
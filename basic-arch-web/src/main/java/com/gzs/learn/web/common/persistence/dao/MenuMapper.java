package com.gzs.learn.web.common.persistence.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.gzs.learn.rbac.inf.MenuNodeDto;
import com.gzs.learn.web.common.persistence.model.Menu;

import tk.mybatis.mapper.common.Mapper;

/**
 * <p>
  * 菜单表 Mapper 接口
 * </p>
 */
public interface MenuMapper extends Mapper<Menu> {

    /**
     * 根据条件查询菜单
     *
     * @return
     */
    List<Map<String, Object>> selectMenus(@Param("condition") String condition, @Param("level") String level);

    /**
     * 根据条件查询菜单
     *
     * @return
     * @date 2017年2月12日 下午9:14:34
     */
    List<Integer> getMenuIdsByRoleId(@Param("roleId") Long roleId);

    /**
     * 删除menu关联的relation
     *
     * @param menuId
     * @return
     * @date 2017年2月19日 下午4:10:59
     */
    int deleteRelationByMenu(Long menuId);

    /**
     * 获取资源url通过角色id
     *
     * @param roleId
     * @return
     * @date 2017年2月19日 下午7:12:38
     */
    List<String> getResUrlsByRoleId(Long roleId);

    /**
     * 根据角色获取菜单
     *
     * @param roleIds
     * @return
     * @date 2017年2月19日 下午10:35:40
     */
    List<MenuNodeDto> getMenusByRoleIds(List<Long> roleIds);

}
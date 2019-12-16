package com.gzs.learn.rbac.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gzs.learn.common.util.BeanUtil;
import com.gzs.learn.rbac.dao.MenuMapper;
import com.gzs.learn.rbac.dao.RelationMapper;
import com.gzs.learn.rbac.inf.MenuDto;
import com.gzs.learn.rbac.inf.MenuNodeDto;
import com.gzs.learn.rbac.inf.ZTreeNode;
import com.gzs.learn.rbac.po.MenuPo;
import com.gzs.learn.rbac.po.RelationPo;
import com.gzs.learn.rbac.service.IMenuService;

import tk.mybatis.mapper.entity.Example;

@Component
@Transactional
public class MenuServiceImpl implements IMenuService {
    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RelationMapper relationMapper;

    @Override
    public List<String> findPermissionsByRoleId(List<Long> roleId) {
        List<String> resUrls = menuMapper.getResUrlsByRoleIds(roleId);
        return resUrls;
    }

    @Override
    public MenuDto getMenu(Long menuId) {
        MenuPo menuPo = menuMapper.selectByPrimaryKey(menuId);
        MenuDto menuDto = new MenuDto();
        BeanUtil.copyProperties(menuPo, menuDto);
        return menuDto;
    }

    @Override
    public List<MenuDto> getMenu(List<Long> menuIds) {
        Example menuExample = new Example(MenuPo.class);
        menuExample.createCriteria().andIn("id", menuIds);
        List<MenuPo> list = menuMapper.selectByExample(menuExample);
        List<MenuDto> results = BeanUtil.copyList(list, MenuDto.class);
        return results;
    }

    @Override
    public MenuDto getMenuByCode(String code) {
        MenuPo menuPo = new MenuPo();
        menuPo.setCode(code);
        menuPo = menuMapper.selectOne(menuPo);

        MenuDto menuDto = new MenuDto();
        BeanUtils.copyProperties(menuPo, menuDto);
        return menuDto;
    }

    @Override
    public List<MenuDto> searchMenu(String menuName, String level) {
        List<MenuPo> menuPos = menuMapper.searchMenus(menuName, level);
        List<MenuDto> results = BeanUtil.copyList(menuPos, MenuDto.class);
        return results;
    }

    @Override
    public boolean updateMenu(MenuDto menu) {
        MenuPo menuPo = new MenuPo();
        BeanUtil.copyProperties(menu, menuPo);
        return menuMapper.updateByPrimaryKeySelective(menuPo) == 1;
    }

    @Override
    public boolean insertMenu(MenuDto menu) {
        MenuPo menuPo = new MenuPo();
        BeanUtil.copyProperties(menu, menuPo);
        return menuMapper.insertSelective(menuPo) == 1;
    }

    @Override
    public List<ZTreeNode> menuTreeList() {
        return menuMapper.menuTreeList();
    }

    @Override
    public boolean delMenuContainSunMenus(Long menuId) {
        MenuPo menuPo = menuMapper.selectByPrimaryKey(menuId);
        String code = menuPo.getCode();
        Example example = new Example(MenuPo.class);
        example.createCriteria().andLike("pcode", "%[" + code + "]%");
        List<MenuPo> subMenuPos = menuMapper.selectByExample(example);
        List<Long> subMenuIds = subMenuPos.stream().map(s -> s.getId()).collect(Collectors.toList());
        subMenuIds.add(menuId);
        Example delExample = new Example(MenuPo.class);
        delExample.createCriteria().andIn("id", subMenuIds);
        // 清除菜单项目
        menuMapper.deleteByExample(delExample);
        Example delRelationExample = new Example(RelationPo.class);
        // 清除菜单关联项目
        delRelationExample.createCriteria().andIn("menuid", subMenuIds);
        relationMapper.deleteByExample(delRelationExample);
        return true;
    }

    @Override
    public List<Long> getMenuIdsByRoleId(Long roleId) {
        List<Long> menuIds = menuMapper.getMenuIdsByRoleId(roleId);
        return menuIds;
    }

    @Override
    public List<ZTreeNode> menuTreeListByMenuIds(List<Long> menuIds) {
        List<ZTreeNode> menuTree = menuMapper.menuTreeListByMenuIds(menuIds);
        return menuTree;
    }

    @Override
    public List<MenuNodeDto> getMenuIdsByRoleIds(List<Long> roleList) {
        List<MenuNodeDto> menus = menuMapper.getMenusByRoleIds(roleList);
        return menus;
    }
}

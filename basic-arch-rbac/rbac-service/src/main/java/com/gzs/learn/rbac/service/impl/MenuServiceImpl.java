package com.gzs.learn.rbac.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.gzs.learn.rbac.dao.MenuMapper;
import com.gzs.learn.rbac.inf.MenuDto;
import com.gzs.learn.rbac.po.MenuPo;
import com.gzs.learn.rbac.service.IMenuService;

import tk.mybatis.mapper.entity.Example;

@Component
public class MenuServiceImpl implements IMenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<String> findPermissionsByRoleId(Integer roleId) {
        List<String> resUrls = menuMapper.getResUrlsByRoleId(roleId);
        return resUrls;
    }

    @Override
    public MenuDto getMenu(Integer menuId) {
        MenuPo menuPo = menuMapper.selectByPrimaryKey(menuId);
        MenuDto menuDto = new MenuDto();
        BeanUtils.copyProperties(menuPo, menuDto);
        return menuDto;
    }

    @Override
    public List<MenuDto> getMenu(List<Integer> menuIds) {
        Example menuExample = new Example(MenuPo.class);
        menuExample.createCriteria().andIn("id", menuIds);
        List<MenuPo> list = menuMapper.selectByExample(menuExample);
        List<MenuDto> results = Lists.newArrayListWithCapacity(list.size());
        for (MenuPo po : list) {
            MenuDto menuDto = new MenuDto();
            BeanUtils.copyProperties(po, menuDto);
            results.add(menuDto);
        }
        return results;
    }

    @Override
    public MenuDto getMenuByCode(String code) {
        MenuPo menuPo = new MenuPo();
        menuPo.setCode(code);
        menuPo = menuMapper.selectOne(menuPo);
        
        return null;
    }

}

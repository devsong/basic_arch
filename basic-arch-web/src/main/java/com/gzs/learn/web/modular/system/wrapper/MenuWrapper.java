package com.gzs.learn.web.modular.system.wrapper;

import java.util.List;
import java.util.Map;

import com.gzs.learn.rbac.inf.MenuDto;
import com.gzs.learn.web.common.BaseControllerWrapper;
import com.gzs.learn.web.common.constant.enums.IsMenu;
import com.gzs.learn.web.common.constant.factory.ConstantFactory;

/**
 * 菜单列表的包装类
 *
 * @author fengshuonan
 * @date 2017年2月19日15:07:29
 */
public class MenuWrapper extends BaseControllerWrapper {

    public MenuWrapper(List<MenuDto> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        map.put("statusName", ConstantFactory.me().getMenuStatusName((Integer) map.get("status")));
        map.put("isMenuName", IsMenu.valueOf((Integer) map.get("ismenu")));
    }

}

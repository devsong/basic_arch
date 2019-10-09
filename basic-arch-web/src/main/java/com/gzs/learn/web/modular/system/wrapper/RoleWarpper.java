package com.gzs.learn.web.modular.system.wrapper;

import java.util.List;
import java.util.Map;

import com.gzs.learn.rbac.inf.RoleDto;
import com.gzs.learn.web.common.constant.factory.ConstantFactory;
import com.gzs.learn.web.common.warpper.BaseControllerWarpper;
import com.gzs.learn.web.core.util.Convert;

/**
 * 角色列表的包装类
 *
 * @author fengshuonan
 * @date 2017年2月19日10:59:02
 */
public class RoleWarpper extends BaseControllerWarpper {

    public RoleWarpper(List<RoleDto> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        map.put("pName", ConstantFactory.me().getSingleRoleName(Convert.toLong(map.get("pid"))));
        map.put("deptName", ConstantFactory.me().getDeptName(Convert.toLong(map.get("deptid"))));
    }

}

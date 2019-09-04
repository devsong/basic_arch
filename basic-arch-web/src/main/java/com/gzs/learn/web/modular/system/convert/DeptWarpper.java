package com.gzs.learn.web.modular.system.convert;

import java.util.Map;

import com.gzs.learn.web.common.constant.factory.ConstantFactory;
import com.gzs.learn.web.common.warpper.BaseControllerWarpper;
import com.gzs.learn.web.core.util.ToolUtil;

/**
 * 部门列表的包装
 *
 * @author fengshuonan
 * @date 2017年4月25日 18:10:31
 */
public class DeptWarpper extends BaseControllerWarpper {

    public DeptWarpper(Object list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {

        Integer pid = (Integer) map.get("pid");

        if (ToolUtil.isEmpty(pid) || pid.equals(0)) {
            map.put("pName", "--");
        } else {
            map.put("pName", ConstantFactory.me().getDeptName(pid));
        }
    }

}

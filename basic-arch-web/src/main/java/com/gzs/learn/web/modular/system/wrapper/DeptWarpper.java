package com.gzs.learn.web.modular.system.wrapper;

import java.util.Map;

import com.gzs.learn.web.common.BaseControllerWarpper;
import com.gzs.learn.web.common.constant.factory.ConstantFactory;
import com.gzs.learn.web.core.util.Convert;
import com.gzs.learn.web.core.util.ToolUtil;

/**
 * 部门列表的包装
 *
 * @author fengshuonan
 * @date 2017年4月25日 18:10:31
 */
public class DeptWarpper extends BaseControllerWarpper {

    public DeptWarpper(Object obj) {
        super(obj);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        Long pid = Convert.toLong(map.get("pid"));

        if (ToolUtil.isEmpty(pid) || pid.equals(0L)) {
            map.put("pName", "--");
        } else {
            map.put("pName", ConstantFactory.me().getDeptName(pid));
        }
    }

}

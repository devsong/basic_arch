package com.gzs.learn.web.modular.system.wrapper;

import static com.gzs.learn.web.core.util.Convert.toInt;
import static com.gzs.learn.web.core.util.Convert.toLong;
import static com.gzs.learn.web.core.util.Convert.toStr;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.gzs.learn.rbac.inf.UserDto;
import com.gzs.learn.web.common.BaseControllerWrapper;
import com.gzs.learn.web.common.constant.factory.ConstantFactory;

/**
 * 用户管理的包装类
 *
 * @author fengshuonan
 * @date 2017年2月13日 下午10:47:03
 */
public class UserWrapper extends BaseControllerWrapper {

    public UserWrapper(List<UserDto> list) {
        super(list);
    }

    public UserWrapper(UserDto obj) {
        super(obj);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        map.put("birthday", DateFormatUtils.format((Date) map.get("birthday"), "yyyy-MM-dd"));
        map.put("sexName", ConstantFactory.me().getSexName(toInt(map.get("sex"))));
        map.put("roleName", ConstantFactory.me().getRoleName(toStr(map.get("roleid"))));
        map.put("deptName", ConstantFactory.me().getDeptName(toLong(map.get("deptid"))));
        map.put("statusName", ConstantFactory.me().getStatusName(toInt(map.get("status"))));
    }

}
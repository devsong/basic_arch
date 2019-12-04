package com.gzs.learn.web.modular.system.wrapper;

import java.util.Map;

import com.gzs.learn.web.common.BaseControllerWrapper;
import com.gzs.learn.web.common.constant.factory.ConstantFactory;
import com.gzs.learn.web.core.util.Convert;

/**
 * 部门列表的包装
 *
 * @author fengshuonan
 * @date 2017年4月25日 18:10:31
 */
public class NoticeWrapper extends BaseControllerWrapper {

    public NoticeWrapper(Object list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        Long creater = Convert.toLong(map.get("creater"));
        map.put("createrName", ConstantFactory.me().getUserNameById(creater));
    }

}

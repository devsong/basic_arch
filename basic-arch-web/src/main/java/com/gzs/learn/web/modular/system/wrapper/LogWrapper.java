package com.gzs.learn.web.modular.system.wrapper;

import java.util.Map;

import com.gzs.learn.web.common.BaseControllerWrapper;
import com.gzs.learn.web.common.constant.factory.ConstantFactory;
import com.gzs.learn.web.core.util.Contrast;
import com.gzs.learn.web.core.util.Convert;
import com.gzs.learn.web.core.util.ToolUtil;

/**
 * 日志列表的包装类
 *
 * @author fengshuonan
 * @date 2017年4月5日22:56:24
 */
public class LogWrapper extends BaseControllerWrapper {

    public LogWrapper(Object list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        String message = (String) map.get("msg");
        Long userid = Convert.toLong(map.get("userid"));
        map.put("userName", ConstantFactory.me().getUserNameById(userid));

        // 如果信息过长,则只截取前100位字符串
        if (ToolUtil.isNotEmpty(message) && message.length() >= 100) {
            String subMessage = message.substring(0, 100) + "...";
            map.put("msg", subMessage);
        }

        // 如果信息中包含分割符号;;; 则分割字符串返给前台
        if (ToolUtil.isNotEmpty(message) && message.indexOf(Contrast.separator) != -1) {
            String[] msgs = message.split(Contrast.separator);
            map.put("regularMessage", msgs);
        } else {
            map.put("regularMessage", message);
        }
    }

}

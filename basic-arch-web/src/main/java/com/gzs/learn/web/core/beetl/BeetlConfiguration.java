package com.gzs.learn.web.core.beetl;

import java.util.HashMap;
import java.util.Map;

import org.beetl.ext.spring.BeetlGroupUtilConfiguration;

import com.gzs.learn.web.common.constant.Const;
import com.gzs.learn.web.core.util.KaptchaUtil;
import com.gzs.learn.web.core.util.ToolUtil;

public class BeetlConfiguration extends BeetlGroupUtilConfiguration {

    @Override
    public void initOther() {
        // 全局共享变量
        Map<String, Object> shared = new HashMap<>();
        shared.put("systemName", Const.DEFAULT_SYSTEM_NAME);
        shared.put("welcomeTip", Const.DEFAULT_WELCOME_TIP);
        shared.put("_t", System.currentTimeMillis());
        groupTemplate.setSharedVars(shared);

        // 全局共享方法
        groupTemplate.registerFunctionPackage("shiro", new ShiroExt());
        groupTemplate.registerFunctionPackage("tool", new ToolUtil());
        groupTemplate.registerFunctionPackage("kaptcha", new KaptchaUtil());
    }

}

package com.ruoyi.web.core.config;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import com.ruoyi.common.config.Global;
import com.ruoyi.common.constant.Constants;

import lombok.extern.slf4j.Slf4j;

@Configuration
@DependsOn("global")
@Slf4j
public class ThymeleafConfig {
    @Resource
    private void configureThymeleafStaticVars(ThymeleafViewResolver viewResolver) {
        log.info("configureThymeleafStaticVars 配置thymeleaf静态变量");
        // 页面全局变量
        if (viewResolver != null) {
            Map<String, Object> vars = new HashMap<>();
            // 版本号
            vars.put("_v", Global.getVersion());
            // 系统名称
            vars.put("_sys_name", Constants.SYSTEM_NAME);
            viewResolver.setStaticVariables(vars);
        }
    }
}

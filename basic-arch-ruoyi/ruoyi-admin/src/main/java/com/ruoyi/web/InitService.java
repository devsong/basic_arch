package com.ruoyi.web;

import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.gzs.learn.common.util.ClassUtil;
import com.gzs.learn.common.util.IpUtil;
import com.gzs.learn.log.dubbo.DubboPerfLogService;
import com.gzs.learn.log.inf.SysPerfLogDto;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.framework.config.ApplicationConfig;

public class InitService {

    @Autowired
    private ApplicationConfig applicationConfig;

    @Autowired
    private DubboPerfLogService dubboPerfLogService;

    @PostConstruct
    public void init() {
        registerPerfMetaData();
    }

    private void registerPerfMetaData() {
        String operatorIp = IpUtil.getLocalIp();
        List<Class<?>> classes = ClassUtil.getClass(Constants.SYSTEM_PREFIX + ".web.controller", true);
        for (Class<?> clazz : classes) {
            if (clazz.isInterface()) {
                continue;
            }
            String className = clazz.getName();
            for (Method m : clazz.getDeclaredMethods()) {
                String methodName = m.getName();
                SysPerfLogDto sysPerfLogDto = SysPerfLogDto.builder().product(applicationConfig.getProduct())
                        .groupName(applicationConfig.getGroup()).app(applicationConfig.getApp()).clazz(className).method(methodName)
                        .operatorIp(operatorIp).build();
                dubboPerfLogService.insertPerfLogMeta(sysPerfLogDto);
            }
        }
    }
}

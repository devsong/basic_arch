package com.gzs.learn.log;

import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gzs.learn.common.util.ClassUtil;
import com.gzs.learn.common.util.IpUtil;
import com.gzs.learn.log.config.LogProperties;
import com.gzs.learn.log.po.SysPerfLogMetaPo;
import com.gzs.learn.log.service.IPerfLogService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class InitService {

    @Autowired
    private LogProperties logProperties;

    @Autowired
    private IPerfLogService perfLogService;

    @PostConstruct
    public void init() {
        log.info("init execute...");

        registerPerfMetaData();
    }

    private void registerPerfMetaData() {
        String operatorIp = IpUtil.getLocalIp();
        List<Class<?>> classes = ClassUtil.getClass("com.gzs.learn.log.dubbo", true);
        for (Class<?> clazz : classes) {
            if (clazz.isInterface()) {
                continue;
            }
            String className = clazz.getName();
            for (Method m : clazz.getDeclaredMethods()) {
                String methodName = m.getName();
                SysPerfLogMetaPo metaDto = SysPerfLogMetaPo.builder().product(logProperties.getProduct())
                        .groupName(logProperties.getGroupName()).app(logProperties.getApp()).clazz(className).method(methodName)
                        .operatorIp(operatorIp).build();
                perfLogService.insertPerfLogMeta(metaDto);
            }
        }
    }
}

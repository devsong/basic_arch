package com.gzs.learn.rbac;

import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gzs.learn.common.util.ClassUtil;
import com.gzs.learn.common.util.IpUtil;
import com.gzs.learn.log.dubbo.DubboPerfLogService;
import com.gzs.learn.log.inf.SysPerfLogDto;
import com.gzs.learn.rbac.conf.RbacProperties;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class InitService {

    @Autowired
    private RbacProperties rbacProperties;

    //@Autowired
    private DubboPerfLogService dubboPerfLogService;

    @PostConstruct
    public void init() {
        log.info("init execute...");

        // registerPerfMetaData();
    }

    private void registerPerfMetaData() {
        String operatorIp = IpUtil.getLocalIp();
        List<Class<?>> classes = ClassUtil.getClass("com.gzs.learn.rbac.dubbo", true);
        for (Class<?> clazz : classes) {
            if (clazz.isInterface()) {
                continue;
            }
            String className = clazz.getName();
            for (Method m : clazz.getDeclaredMethods()) {
                String methodName = m.getName();
                SysPerfLogDto metaDto = SysPerfLogDto.builder().product(rbacProperties.getProduct())
                        .groupName(rbacProperties.getGroupName()).app(rbacProperties.getApp()).clazz(className).method(methodName)
                        .operatorIp(operatorIp).build();
                dubboPerfLogService.insertPerfLogMeta(metaDto);
            }
        }
    }
}

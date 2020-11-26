package com.ruoyi.web;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.gzs.learn.common.util.ClassUtil;
import com.gzs.learn.common.util.IpUtil;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.log.domain.SysPerfLogMetaPo;
import com.ruoyi.log.service.IPerfLogService;

import lombok.extern.slf4j.Slf4j;

/**
 * 初始化接口元数据信息
 * @author guanzhisong
 */
@Component
@Slf4j
public class InitService {
    private static final String[] BASE_PACKAGE = new String[] { Constants.SYSTEM_PREFIX + ".web.controller",
            Constants.SYSTEM_PREFIX + ".serial.controller", Constants.SYSTEM_PREFIX + ".generator.controller" };

    @Autowired
    private RuoYiConfig ruoYiConfig;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private IPerfLogService perfLogService;

    @PostConstruct
    public void init() {
        log.info("init execute...");
        registerPerfMetaData();
    }

    private void registerPerfMetaData() {
        String operatorIp = IpUtil.getLocalIp();
        for (String pack : BASE_PACKAGE) {
            List<Class<?>> classes = ClassUtil.getClass(pack, true);
            for (Class<?> clazz : classes) {
                if (clazz.isInterface()) {
                    continue;
                }
                String className = clazz.getName();
                for (Method m : clazz.getDeclaredMethods()) {
                    String methodName = m.getName();
                    SysPerfLogMetaPo metaDto = SysPerfLogMetaPo.builder().product(ruoYiConfig.getProduct())
                            .groupName(ruoYiConfig.getGroup()).app(ruoYiConfig.getApp()).clazz(className).method(methodName)
                            .operatorIp(operatorIp).createTime(new Date()).build();
                    // 异步执行
                    threadPoolTaskExecutor.submit(() -> perfLogService.insertPerfLogMeta(metaDto));
                }
            }
        }
    }
}

package com.ruoyi.framework.aspectj;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.google.common.base.Stopwatch;
import com.gzs.learn.common.util.IpUtil;
import com.gzs.learn.common.util.JsonUtil;
import com.gzs.learn.common.util.SpringContextHolder;
import com.gzs.learn.log.dubbo.DubboPerfLogService;
import com.gzs.learn.log.enums.SysPerfLogDurationEnum;
import com.gzs.learn.log.inf.SysPerfLogDto;
import com.ruoyi.framework.config.ApplicationConfig;

import lombok.extern.slf4j.Slf4j;

/**
 * 性能日志
 * 
 * @author guanzhisong
 */
@Aspect
@Component
@Slf4j
public class SysPerfLogAspect {
    public static final String LOG_POINTCUT = "@within(org.springframework.stereotype.Controller)";

    @Autowired
    private ApplicationConfig applicationConfig;

    @Autowired
    private DubboPerfLogService dubboPerfLogService;

    // 配置织入点
    @Pointcut(LOG_POINTCUT)
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object recordLog(ProceedingJoinPoint point) throws Throwable {
        // 访问目标方法的参数：
        Stopwatch stopwatch = Stopwatch.createStarted();
        MethodSignature sig = (MethodSignature) point.getSignature();
        Object target = point.getTarget();
        Method currentMethod = target.getClass().getMethod(sig.getName(), sig.getParameterTypes());
        String clazz = sig.getDeclaringTypeName();
        String methodName = sig.getName();
        Object[] args = point.getArgs();
        Object returnValue = null;
        Exception exception = null;
        try {
            returnValue = point.proceed(args);
        } catch (Exception e) {
            // 记录系统调用异常日志
            exception = e;
            log.error("system error,method:{} args:{}", currentMethod.getName(), JsonUtil.toJSONString(point.getArgs()), e);
            throw e;
        } finally {
            // 记录方法调用日志
            stopwatch.stop();
            long elapsed = stopwatch.elapsed(TimeUnit.MILLISECONDS);
            recordPerfLog(clazz, methodName, args, returnValue, exception, elapsed);
        }
        return returnValue;
    }

    // 记录性能日志
    private void recordPerfLog(String clazz, String methodName, Object[] args, Object returnValue, Exception exception, long elapsed) {
        int code = 0;
        String errorMsg = "success";
        if (exception != null) {
            // 异常非空,系统异常
            code = -1;
            errorMsg = JsonUtil.toJSONString(exception);
        } else {
            if (returnValue == null) {
                // 返回值位空,业务异常
                code = 1;
                errorMsg = "null";
            }
        }

        String argsJson = (args == null || args.length == 0) ? "" : JsonUtil.toJSONString(args);
        String retJson = (returnValue == null ? "" : JsonUtil.toJSONString(returnValue));
        SysPerfLogDto sysPerfLogDto = SysPerfLogDto.builder().product(applicationConfig.getProduct())
                .groupName(applicationConfig.getGroup()).app(applicationConfig.getApp()).clazz(clazz).method(methodName).paramsIn(argsJson)
                .paramsOut(retJson).code(code).errMsg(errorMsg).operatorIp(IpUtil.getLocalIp())
                .durationEnum(SysPerfLogDurationEnum.BY_MINUTE).executeTimespan((int) elapsed).createTime(new Date()).build();

        SpringContextHolder.getBean(ThreadPoolTaskExecutor.class).execute(() -> {
            dubboPerfLogService.insertPerflog(sysPerfLogDto);
        });
    }
}
package com.gzs.learn.log.aop;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Stopwatch;
import com.gzs.learn.common.util.IpUtil;
import com.gzs.learn.common.util.JsonUtil;
import com.gzs.learn.log.ILogConstant;
import com.gzs.learn.log.config.LogProperties;
import com.gzs.learn.log.enums.SysPerfLogDurationEnum;
import com.gzs.learn.log.inf.SysPerfLogDto;
import com.gzs.learn.log.service.IPerfLogService;

import lombok.extern.slf4j.Slf4j;

@Order(1)
@Aspect
@Component
@Slf4j
public class PerformanceAop {

    @Autowired
    private LogProperties logProperties;

    @Autowired
    private IPerfLogService perfLogService;

    @Pointcut("execution(* com.gzs.learn.log.dubbo.*Impl.*(..))")
    public void log4Perf() {

    }

    @Around("log4Perf()")
    public Object aroundMethod(ProceedingJoinPoint point) throws Throwable {
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
            log.error("system error,method:{} args:{}", currentMethod.getName(), JSON.toJSONString(point.getArgs()), e);
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
        SysPerfLogDto sysPerfLogDto = SysPerfLogDto.builder().product(logProperties.getProduct()).groupName(logProperties.getGroupName())
                .app(logProperties.getApp()).clazz(clazz).method(methodName).paramsIn(argsJson).paramsOut(retJson).code(code)
                .errMsg(errorMsg).operatorIp(IpUtil.getLocalIp()).durationEnum(SysPerfLogDurationEnum.BY_MINUTE)
                .executeTimespan((int) elapsed).createTime(new Date()).build();

        ILogConstant.EXECUTOR.execute(() -> {
            perfLogService.insertPerfLog(sysPerfLogDto);
        });
    }
}

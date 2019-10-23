package com.gzs.learn.config.aop;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Stopwatch;
import com.gzs.learn.common.util.IpUtil;
import com.gzs.learn.common.util.JsonUtil;
import com.gzs.learn.config.IConfigConstant;
import com.gzs.learn.config.conf.ConfProperties;
import com.gzs.learn.log.dubbo.DubboPerfLogService;
import com.gzs.learn.log.enums.SysPerfLogDurationEnum;
import com.gzs.learn.log.inf.SysPerfLogDto;

@Aspect
@Component
public class PerformanceAop {

    @Autowired
    private ConfProperties confProperties;

    @Autowired
    private DubboPerfLogService dubboPerfLogService;

    @Pointcut("execution(* com.gzs.learn.config.dubbo.*Impl.*(..))")
    public void log4Perf() {

    }

    @Around("log4Perf()")
    public Object aroundMethod(ProceedingJoinPoint point) throws Throwable {
        // 访问目标方法的参数：
        Stopwatch stopwatch = Stopwatch.createStarted();
        MethodSignature sig = (MethodSignature) point.getSignature();
        String clazz = sig.getDeclaringTypeName();
        String methodName = sig.getName();
        Object[] args = point.getArgs();
        Object returnValue = null;
        Exception exception = null;
        try {
            returnValue = point.proceed(args);
        } catch (Exception e) {
            exception = e;
        } finally {
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
        SysPerfLogDto sysPerfLogDto = SysPerfLogDto.builder().product(confProperties.getProduct()).groupName(confProperties.getGroupName())
                .app(confProperties.getApp()).clazz(clazz).method(methodName).paramsIn(JsonUtil.toJSONString(args))
                .paramsOut(returnValue == null ? "" : JsonUtil.toJSONString(returnValue)).code(code).errMsg(errorMsg)
                .operatorIp(IpUtil.getLocalIp()).durationEnum(SysPerfLogDurationEnum.BY_MINUTE).executeTimespan((int) elapsed)
                .createTime(new Date()).build();

        IConfigConstant.EXECUTOR.execute(() -> {
            dubboPerfLogService.insertPerfLogMeta(sysPerfLogDto);
        });
    }
}

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
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Stopwatch;
import com.gzs.learn.common.util.IpUtil;
import com.gzs.learn.common.util.JsonUtil;
import com.gzs.learn.inf.PageResponseDto;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.framework.manager.AsyncManager;
import com.ruoyi.framework.manager.factory.AsyncFactory;
import com.ruoyi.log.dto.SysPerfLogDto;
import com.ruoyi.log.enums.SysPerfLogDurationEnum;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class PerformanceAop {
    private static final String EXECUTION_AOP = "within(" + Constants.SYSTEM_PREFIX + ".web.controller..*)";

    @Autowired
    private RuoYiConfig ruoYiConfig;

    /**
     * 定义切入点
     */
    @Pointcut("execution(* " + EXECUTION_AOP + ")")
    public void log4Perf() {

    }

    @Around("log4Perf()")
    public Object aroundMethod(ProceedingJoinPoint point) throws Throwable {
        // 访问目标方法的参数：
        MethodSignature sig = (MethodSignature) point.getSignature();
        Object target = point.getTarget();
        Method currentMethod = target.getClass().getMethod(sig.getName(), sig.getParameterTypes());
        String clazz = sig.getDeclaringTypeName();
        String methodName = sig.getName();
        Object[] args = point.getArgs();
        Object returnValue = null;
        Exception exception = null;
        long elapsed = 0;
        Stopwatch stopwatch = Stopwatch.createStarted();
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
            elapsed = stopwatch.elapsed(TimeUnit.MILLISECONDS);
            if (returnValue instanceof PageResponseDto) {
                ((PageResponseDto<?>) returnValue).setElapsed(elapsed);
                ((PageResponseDto<?>) returnValue).setServerIp(IpUtil.getLocalIp());
            }
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
        SysPerfLogDto sysPerfLogDto = SysPerfLogDto.builder().product(ruoYiConfig.getProduct()).groupName(ruoYiConfig.getGroup())
                .app(ruoYiConfig.getApp()).clazz(clazz).method(methodName).paramsIn(argsJson).paramsOut(retJson).code(code).errMsg(errorMsg)
                .operatorIp(IpUtil.getLocalIp()).durationEnum(SysPerfLogDurationEnum.BY_MINUTE).executeTimespan((int) elapsed)
                .createTime(new Date()).build();

        AsyncManager.me().execute(AsyncFactory.recordPerfLog(sysPerfLogDto));
    }
}

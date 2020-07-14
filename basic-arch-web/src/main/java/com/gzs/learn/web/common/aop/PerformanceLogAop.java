package com.gzs.learn.web.common.aop;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.gzs.learn.common.util.IpUtil;
import com.gzs.learn.common.util.JsonUtil;
import com.gzs.learn.log.enums.SysPerfLogDurationEnum;
import com.gzs.learn.log.inf.SysPerfLogDto;
import com.gzs.learn.web.common.constant.CommonResponse;
import com.gzs.learn.web.common.constant.Const;
import com.gzs.learn.web.config.properties.GunsProperties;
import com.gzs.learn.web.modular.biz.service.ISystemLogService;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class PerformanceLogAop {
    private static final String EXECUTION_AOP = Const.EXECUTION_AOP_PREFIX + ".common.annotion.PerformanceAnnotation";

    @Autowired
    private GunsProperties gunsProperties;

    @Autowired
    private ISystemLogService systemLogService;

    /**
     * 对所有controller切片 (方法说明描述)
     */
    @Pointcut("@annotation(org.springframework.web.bind.annotation.ResponseBody)")
    public void log4Performance() {
    }

    @Around("log4Performance()")
    public Object aroundController(ProceedingJoinPoint point) throws Throwable {
        return log4Performance(point);
    }

    @Pointcut("@annotation(" + EXECUTION_AOP + ")")
    public void log4Perf() {
    }

    @Around("log4Perf()")
    public Object aroundMethod(ProceedingJoinPoint point) throws Throwable {
        return log4Performance(point);
    }

    private Object log4Performance(ProceedingJoinPoint point) throws Throwable, Exception {
        Object[] args = point.getArgs();
        Object returnValue = null;
        Exception exception = null;
        boolean success = true;
        long elapsed = 0;
        Stopwatch stopwatch = Stopwatch.createStarted();
        try {
            returnValue = point.proceed(args);
        } catch (Exception e) {
            exception = e;
            success = false;
            throw e;
        } finally {
            stopwatch.stop();
            elapsed = stopwatch.elapsed(TimeUnit.MICROSECONDS);
            if (returnValue instanceof CommonResponse) {
                ((CommonResponse<?>) returnValue).setElapsed(elapsed);
                ((CommonResponse<?>) returnValue).setServerIp(IpUtil.getLocalIp());
            }
            postProcess(point, success, returnValue, exception, elapsed);
        }
        return returnValue;
    }

    private void postProcess(ProceedingJoinPoint point, boolean success, Object ret, Exception exception, long cost) {
        point.getSignature();
        MethodSignature mSignature = (MethodSignature) point.getSignature();
        Object target = point.getTarget();
        Method currentMethod = null;
        try {
            currentMethod = target.getClass().getMethod(mSignature.getName(), mSignature.getParameterTypes());
        } catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
        if (currentMethod == null) {
            log.error("get current method error");
            return;
        }

        String methodName = currentMethod.getName();

        // 获取拦截方法的参数
        String className = point.getTarget().getClass().getName();
        Object[] params = filterParam(point.getArgs());

        String argsJson = (params == null || params.length == 0) ? "" : JsonUtil.toJSONString(params);
        String retJson = (ret == null ? "" : JsonUtil.toJSONString(ret));

        SysPerfLogDto sysPerfLogDto = SysPerfLogDto.builder().product(gunsProperties.getProduct()).groupName(gunsProperties.getGroupName())
                .app(gunsProperties.getApp()).clazz(className).method(methodName).executeTimespan((int) cost).code(success ? 0 : 1)
                .errMsg(exception == null ? "" : JsonUtil.toJSONString(exception)).paramsIn(argsJson).paramsOut(retJson)
                .createTime(new Date()).durationEnum(SysPerfLogDurationEnum.BY_MINUTE).build();
        systemLogService.savePerfLog(sysPerfLogDto);
    }

    private Object[] filterParam(Object[] args) {
        String ignorePackage = "org.springframework.validation";
        List<Object> list = Lists.newArrayListWithCapacity(args.length);
        for (Object obj : args) {
            if (obj == null) {
                continue;
            }
            String className = obj.getClass().getName();
            if (className.startsWith(ignorePackage)) {
                continue;
            }
            list.add(obj);
        }
        return list.toArray();
    }
}

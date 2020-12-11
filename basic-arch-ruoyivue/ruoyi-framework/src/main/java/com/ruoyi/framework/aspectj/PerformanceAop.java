package com.ruoyi.framework.aspectj;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Stopwatch;
import com.gzs.learn.common.util.IpUtil;
import com.gzs.learn.common.util.JsonUtil;
import com.ruoyi.common.annotation.PerfLog;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.framework.manager.AsyncManager;
import com.ruoyi.framework.manager.factory.AsyncFactory;
import com.ruoyi.log.dto.SysPerfLogDto;
import com.ruoyi.log.enums.SysPerfLogDurationEnum;
import lombok.extern.slf4j.Slf4j;

/**
 * 记录接口请求中的参数,类似于nginx/httpd中的access log功能
 * 
 * @author guanzhisong
 */
@Aspect
@Component
@Slf4j
public class PerformanceAop {
    private static final int MAX_STR_LEN = 2 << 12;
    private static final String WEB_AOP = "within(" + Constants.SYSTEM_PREFIX + ".*.controller..*)";
    private static final String ANNOTATION_PERLOG = "@annotation(" + Constants.SYSTEM_PREFIX + ".common.annotation.PerfLog)";

    @Autowired
    private RuoYiConfig ruoYiConfig;

    @Pointcut(WEB_AOP + "||" + ANNOTATION_PERLOG)
    public void log4Perf() {
    }

    @Around("log4Perf()")
    public Object aroundMethod(ProceedingJoinPoint point) throws Throwable {
        // 访问目标方法的参数：
        MethodSignature sig = (MethodSignature) point.getSignature();
        Object target = point.getTarget();
        Method currentMethod = target.getClass().getMethod(sig.getName(), sig.getParameterTypes());
        String clazz = sig.getDeclaringTypeName();
        String methodName = currentMethod.getName();
        PerfLog perfLog = getPerfLog(point);
        boolean recordFlag = true;
        if (perfLog != null && perfLog.ignore()) {
            // 指定类不记录日志
            recordFlag = false;
        }
        if (perfLog != null) {
            clazz = StringUtils.isNotBlank(perfLog.clazz()) ? perfLog.clazz() : clazz;
            methodName = StringUtils.isNotBlank(perfLog.method()) ? perfLog.method() : methodName;
        }
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
            log.error("system error,method:{} args:{}", methodName, JsonUtil.toJSONString(point.getArgs()), e);
            throw e;
        } finally {
            // 记录方法调用日志
            stopwatch.stop();
            elapsed = stopwatch.elapsed(TimeUnit.MILLISECONDS);
            if (recordFlag) {
                // 记录日志
                recordPerfLog(clazz, methodName, args, returnValue, exception, elapsed);
            }
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

        String argsJson = (args == null || args.length == 0) ? "" : JSON.toJSONString(args);
        String retJson = (returnValue == null ? "" : JSON.toJSONString(returnValue));
        argsJson = StringUtils.left(argsJson, MAX_STR_LEN);
        retJson = StringUtils.left(retJson, MAX_STR_LEN);
        errorMsg = StringUtils.left(errorMsg, MAX_STR_LEN);
        SysPerfLogDto sysPerfLogDto = SysPerfLogDto.builder().product(ruoYiConfig.getProduct()).groupName(ruoYiConfig.getGroup())
                .app(ruoYiConfig.getApp()).clazz(clazz).method(methodName).paramsIn(argsJson).paramsOut(retJson).code(code).errmsg(errorMsg)
                .operatorIp(IpUtil.getLocalIp()).durationEnum(SysPerfLogDurationEnum.BY_MINUTE).executeTimespan((int) elapsed)
                .createTime(new Date()).build();

        AsyncManager.me().execute(AsyncFactory.recordPerfLog(sysPerfLogDto));
    }

    private PerfLog getPerfLog(ProceedingJoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        PerfLog dataSource = AnnotationUtils.findAnnotation(signature.getMethod(), PerfLog.class);
        if (Objects.nonNull(dataSource)) {
            return dataSource;
        }

        return AnnotationUtils.findAnnotation(signature.getDeclaringType(), PerfLog.class);
    }
}

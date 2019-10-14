package com.gzs.learn.rbac.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Aspect
@Component
public class DubboAop {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Pointcut(value = "execution(* com.gzs.learn.rbac.dubbo..*.*(..))")
    private void cut() {

    }

    @Around("cut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Object target = point.getTarget();
        Method currentMethod = target.getClass().getMethod(signature.getName(), signature.getParameterTypes());
        try {
            return point.proceed();
        } catch (Exception e) {
            log.error("system error,method:{} args:{}", currentMethod.getName(), JSON.toJSONString(point.getArgs()), e);
            throw e;
        }
    }
}

package com.gzs.learn.web.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.gzs.learn.web.common.controller.BaseController;
import com.gzs.learn.web.core.util.HttpSessionHolder;

/**
 * 静态调用session的拦截器
 */
@Aspect
@Component
@Order(AopOrder.SESSION_ORDER)
public class SessionAop extends BaseController {
    @Pointcut("within(@org.springframework.stereotype.Controller *)")
    public void cutService() {
    }

    @Around("cutService()")
    public Object sessionKit(ProceedingJoinPoint point) throws Throwable {
        HttpSessionHolder.put(super.getHttpServletRequest().getSession());
        try {
            return point.proceed();
        } finally {
            HttpSessionHolder.remove();
        }
    }
}

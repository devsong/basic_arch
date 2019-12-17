package com.gzs.learn.web.common.aop;

import java.util.Set;

import org.apache.shiro.session.InvalidSessionException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import com.google.common.collect.Sets;
import com.gzs.learn.web.common.controller.BaseController;
import com.gzs.learn.web.core.shiro.ShiroKit;
import com.gzs.learn.web.core.support.HttpKit;

/**
 * 验证session超时的拦截器
 *
 * @author fengshuonan
 * @date 2017年6月7日21:08:48
 */
@Aspect
@Component
@ConditionalOnProperty(prefix = "guns", name = "session-open", havingValue = "true")
public class SessionTimeoutIAop extends BaseController implements Ordered {
    @Value("${ignorePath:/kaptcha,/login,/global/sessionError}")
    private String ignorePath;

    @Pointcut("within(@org.springframework.stereotype.Controller *)")
    public void cutService() {
    }

    @Around("cutService()")
    public Object sessionTimeoutValidate(ProceedingJoinPoint point) throws Throwable {
        String servletPath = HttpKit.getRequest().getServletPath();
        Set<String> ignorePaths = Sets.newHashSet(ignorePath.split(","));
        if (ignorePaths.contains(servletPath)) {
            return point.proceed();
        }
        if (ShiroKit.getSession().getAttribute("sessionFlag") == null) {
            ShiroKit.getSubject().logout();
            throw new InvalidSessionException();
        }
        return point.proceed();
    }

    @Override
    public int getOrder() {
        return AopOrder.SESSION_TIMEOUT_ORDER;
    }
}

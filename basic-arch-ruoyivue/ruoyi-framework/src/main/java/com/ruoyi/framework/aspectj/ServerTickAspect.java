package com.ruoyi.framework.aspectj;

import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.base.Stopwatch;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.AjaxResult;

/**
 * 方法执行耗时
 * 
 * @author guanzhisong
 */
@Aspect
@Component
public class ServerTickAspect {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    private static final String WITHIN_TICKS = "within(" + Constants.SYSTEM_PREFIX + ".web.controller..*)";

    @Pointcut(WITHIN_TICKS)
    public void tickPointCut() {

    }

    @Around("tickPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Stopwatch stopwatch = Stopwatch.createStarted();
        Object ret = point.proceed();
        if (ret != null && ret instanceof AjaxResult) {
            stopwatch.stop();
            long tick = stopwatch.elapsed(TimeUnit.MILLISECONDS);
            ((AjaxResult) ret).put(AjaxResult.TICK, tick);
        }
        return ret;
    }
}

package com.ruoyi.framework.aspectj;

import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.google.common.base.Stopwatch;
import com.gzs.learn.common.util.IpUtil;
import com.gzs.learn.inf.PageResponseDto;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.AjaxResult;

/**
 * 方法执行耗时
 * 
 * @author guanzhisong
 */
@Aspect
@Component
// @Slf4j
public class ServerTickAspect {

    private static final String WITHIN_TICKS = "within(" + Constants.SYSTEM_PREFIX + ".*.controller..*)";

    @Pointcut(WITHIN_TICKS)
    public void tickPointCut() {

    }

    @Around("tickPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Stopwatch stopwatch = Stopwatch.createStarted();
        Object ret = null;
        try {
            ret = point.proceed();
        } finally {
            stopwatch.stop();
            long elapsed = stopwatch.elapsed(TimeUnit.MILLISECONDS);
            if (ret != null && ret instanceof AjaxResult) {
                ((AjaxResult) ret).put(AjaxResult.ELAPSED, elapsed);
                ((AjaxResult) ret).put(AjaxResult.SERVER_IP, IpUtil.getLocalIp());
            }
            if (ret != null && ret instanceof PageResponseDto) {
                ((PageResponseDto<?>) ret).setElapsed(elapsed);
                ((PageResponseDto<?>) ret).setServerIp(IpUtil.getLocalIp());
            }
        }
        return ret;
    }
}

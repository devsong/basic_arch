package com.gzs.learn.log.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.gzs.learn.log.LogSystemConstant;
import com.gzs.learn.log.inf.search.PageSearchRequestDto;
import com.gzs.learn.log.util.LogSystemUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 日志记录
 */
@Aspect
@Component
@Slf4j
public class ParamAop {
    private static final String EXECUTION_AOP = LogSystemConstant.LOG_SYSTEM_PREFIX + ".dubbo.*Impl.*(..)";

    /**
     * 定义切入点
     */
    @Pointcut("execution(* " + EXECUTION_AOP + ")")
    public void cutService() {
    }

    @Around("cutService()")
    public Object setDefaultParam(ProceedingJoinPoint point) throws Throwable {
        // 先执行业务
        Object result = null;
        Object[] args = point.getArgs();
        try {
            if (args.length > 1 && args[0] instanceof PageSearchRequestDto) {
                PageSearchRequestDto pageSearchRequestDto = (PageSearchRequestDto) args[0];
                LogSystemUtil.setDefaultSearchRange(pageSearchRequestDto);
            }
            result = point.proceed();
        } catch (Exception e) {
            log.error("设置默认参数出错!", e);
            throw e;
        }

        return result;
    }
}
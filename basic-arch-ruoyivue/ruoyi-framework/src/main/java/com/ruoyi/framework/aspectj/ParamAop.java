package com.ruoyi.framework.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.framework.DefaultParamUtil;
import com.ruoyi.log.dto.PageSearchRequestDto;

import lombok.extern.slf4j.Slf4j;

/**
 * 日志记录
 */
@Aspect
@Component
@Slf4j
public class ParamAop {

    private static final String EXECUTION_AOP = "within(" + Constants.SYSTEM_PREFIX + ".web.controller..*)";

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
                DefaultParamUtil.setDefaultSearchRange(pageSearchRequestDto);
            }
            result = point.proceed();
        } catch (Exception e) {
            log.error("设置默认参数出错!", e);
            throw e;
        }

        return result;
    }
}
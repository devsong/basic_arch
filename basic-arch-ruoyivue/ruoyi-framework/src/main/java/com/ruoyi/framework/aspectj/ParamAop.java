package com.ruoyi.framework.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.framework.DefaultParamUtil;
import com.ruoyi.log.dto.PageSearchRequestDto;

/**
 * 设置默认的查询参数
 */
@Aspect
@Component
public class ParamAop {

    private static final String EXECUTION_AOP = "within(" + Constants.SYSTEM_PREFIX + ".web.controller..*)";

    /**
     * 定义切入点
     */
    @Pointcut(EXECUTION_AOP)
    public void setParamAop() {
    }

    @Around("setParamAop()")
    public Object setDefaultParam(ProceedingJoinPoint point) throws Throwable {
        Object[] args = point.getArgs();
        if (args.length > 1 && args[0] instanceof PageSearchRequestDto) {
            PageSearchRequestDto pageSearchRequestDto = (PageSearchRequestDto) args[0];
            DefaultParamUtil.setDefaultSearchRange(pageSearchRequestDto);
        }
        Object result = point.proceed();
        return result;
    }
}
package com.ruoyi.framework.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.gzs.learn.inf.PageRequestDto;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.framework.DefaultParamUtil;
import com.ruoyi.log.dto.PageSearchRequestDto;

/**
 * 设置默认的查询参数
 */
@Aspect
@Component
public class ParamAop {

    private static final String WEB_AOP = "within(" + Constants.SYSTEM_PREFIX + ".web.controller..*)";
    private static final String SERIAL_AOP = "within(" + Constants.SYSTEM_PREFIX + ".serial.controller..*)";
    private static final String GENERATOR_AOP = "within(" + Constants.SYSTEM_PREFIX + ".generator.controller..*)";

    /**
     * 定义切入点
     */
    @Pointcut(WEB_AOP + "||" + SERIAL_AOP + "||" + GENERATOR_AOP)
    public void setParamAop() {
    }

    @Around("setParamAop()")
    public Object setDefaultParam(ProceedingJoinPoint point) throws Throwable {
        Object[] args = point.getArgs();
        if (args.length > 1 && args[0] instanceof PageSearchRequestDto) {
            PageSearchRequestDto pageSearchRequestDto = (PageSearchRequestDto) args[0];
            DefaultParamUtil.setDefaultSearchRange(pageSearchRequestDto);
        }
        if (args.length > 1 && args[0] instanceof PageRequestDto) {
            PageRequestDto PageRequestDto = (PageRequestDto) args[0];
            DefaultParamUtil.setDefaultSearchRange(PageRequestDto);
        }
        Object result = point.proceed();
        return result;
    }
}
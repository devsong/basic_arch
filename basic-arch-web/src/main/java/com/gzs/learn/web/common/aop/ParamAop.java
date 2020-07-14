package com.gzs.learn.web.common.aop;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.gzs.learn.log.inf.search.PageSearchRequestDto;

import lombok.extern.slf4j.Slf4j;

/**
 * 日志记录
 */
@Aspect
@Component
@Slf4j
@Order(AopOrder.PARAM_SET_ORDER)
public class ParamAop {
    @Pointcut("within(@org.springframework.stereotype.Controller *)")
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
                if (pageSearchRequestDto == null) {
                    throw new IllegalArgumentException("pageSearchRequestDto must not be null");
                }
                if (pageSearchRequestDto.getPage() == null) {
                    pageSearchRequestDto.setPage(1);
                }
                if (pageSearchRequestDto.getPageSize() == null) {
                    pageSearchRequestDto.setPageSize(10);
                }
                if (pageSearchRequestDto.getCreateTimeEnd() == null) {
                    pageSearchRequestDto.setCreateTimeEnd(new Date());
                }
                if (pageSearchRequestDto.getCreateTimeStart() == null) {
                    // 默认查询最近一个月的数据
                    pageSearchRequestDto.setCreateTimeStart(DateUtils.addMonths(pageSearchRequestDto.getCreateTimeEnd(), -1));
                }
            }
        } catch (Exception e) {
            log.error("设置默认参数出错", e);
        }
        result = point.proceed();
        return result;
    }
}
package com.ruoyi.framework.aspectj;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.config.datasource.DynamicDataSourceContextHolder;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.StringUtils;

/**
 * 多数据源处理
 * 
 * @author guanzhisong
 */
@Aspect
@Order(1)
@Component
public class DataSourceAspect {
    public static final String DATASOURCE_POINT = "@annotation(" + Constants.SYSTEM_PREFIX + ".common.annotation.DataSource)||" + "@within("
            + Constants.SYSTEM_PREFIX + ".common.annotation.DataSource)";

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut(DATASOURCE_POINT)
    public void dsPointCut() {

    }

    @Around("dsPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        DataSource dataSource = getDataSource(point);

        if (StringUtils.isNotNull(dataSource)) {
            DynamicDataSourceContextHolder.setDataSourceType(dataSource.value().name());
        }

        try {
            return point.proceed();
        } finally {
            // 销毁数据源 在执行方法之后
            DynamicDataSourceContextHolder.clearDataSourceType();
        }
    }

    /**
     * 获取需要切换的数据源
     */
    public DataSource getDataSource(ProceedingJoinPoint point) {
        Class<? extends Object> targetClass = point.getTarget().getClass();
        DataSource targetDataSource = targetClass.getAnnotation(DataSource.class);
        if (StringUtils.isNotNull(targetDataSource)) {
            return targetDataSource;
        } else {
            MethodSignature signature = (MethodSignature) point.getSignature();
            Method method = signature.getMethod();
            DataSource dataSource = method.getAnnotation(DataSource.class);
            return dataSource;
        }
    }
}

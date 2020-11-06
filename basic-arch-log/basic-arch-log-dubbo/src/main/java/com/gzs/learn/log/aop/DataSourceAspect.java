package com.gzs.learn.log.aop;

import static com.gzs.learn.inf.GlobalConstant.SYSTEM_PACKAGE_PREFIX;

import java.util.Objects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import com.gzs.learn.common.annotation.DataSource;
import com.gzs.learn.log.config.DynamicDataSourceContextHolder;

/**
 * 多数据源处理
 * 
 * @author guanzhisong
 */
@Aspect
@Component
public class DataSourceAspect {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    private static final String DS_POINT_CUT_AN = "@annotation(" + SYSTEM_PACKAGE_PREFIX + ".common.annotation.DataSource)";
    private static final String WITHIN_POINT_CUT_AN = "@within(" + SYSTEM_PACKAGE_PREFIX + ".common.annotation.DataSource)";

    @Pointcut(DS_POINT_CUT_AN + "||" + WITHIN_POINT_CUT_AN)
    public void dsPointCut() {

    }

    @Around("dsPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        DataSource dataSource = getDataSource(point);
        if (dataSource != null) {
            DynamicDataSourceContextHolder.setDataSourceType(dataSource.value());
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
        MethodSignature signature = (MethodSignature) point.getSignature();
        DataSource dataSource = AnnotationUtils.findAnnotation(signature.getMethod(), DataSource.class);
        if (Objects.nonNull(dataSource)) {
            return dataSource;
        }

        return AnnotationUtils.findAnnotation(signature.getDeclaringType(), DataSource.class);
    }
}

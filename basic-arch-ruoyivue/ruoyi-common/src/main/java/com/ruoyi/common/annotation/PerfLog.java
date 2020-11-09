package com.ruoyi.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 性能日志监控
 * 
 * @author guanzhisong
 *
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PerfLog {
    /**
     * 类名
     * @return
     */
    public String clazz() default "";

    /**
     * 方法名
     * @return
     */
    public String method() default "";
}

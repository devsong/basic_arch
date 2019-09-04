package com.gzs.learn.web.common.annotion;

import java.lang.annotation.*;

@Target({ ElementType.PARAMETER, ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PerformanceAnnotation {

}

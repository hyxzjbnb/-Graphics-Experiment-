package com.hyx.webflux.javaee.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisReactiveCacheGet {
    String key() default "";
    boolean useArgsHash() default false;
}
package com.example.ex3_2_back.annotation;

/**
 * @author hyxzjbnb
 * @create 2024-04-29-21:52
 */
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RateLimit {
    int value() default 100; // 默认的令牌桶容量
}

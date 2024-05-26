package com.hyx.webflux.javaee.config;

import com.hyx.webflux.javaee.handler.TokenBucketFilter;
import com.hyx.webflux.javaee.util.TokenBucket;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author hyxzjbnb
 * @create 2024-05-26-22:02
 */
@Configuration
public class TokenBucketConfig {

    @Bean
    public TokenBucket tokenBucket() {
        return new TokenBucket(10); // 假设令牌桶的容量是100
    }

}
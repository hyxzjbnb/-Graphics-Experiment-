package com.example.ex3_2_back.configuration;

import com.example.ex3_2_back.utils.MyJwtUtil;
import com.example.ex3_2_back.utils.TokenBucket;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyUtilsConfiguration {
    @Bean
    public MyJwtUtil jwtUtil() {
        return new MyJwtUtil();
    }

    @Bean
    public TokenBucket tokenBucket() {
        // 在此可以根据需要指定令牌桶的容量
        return new TokenBucket(100); // 假设令牌桶容量为 100
    }

}

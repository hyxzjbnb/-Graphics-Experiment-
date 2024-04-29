package com.example.ex3_2_back.interceptor;

/**
 * @author hyxzjbnb
 * @create 2024-04-29-21:51
 */
import com.example.ex3_2_back.annotation.RateLimit;
import com.example.ex3_2_back.utils.TokenBucket;
import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import javax.servlet.http.HttpServletResponse;


@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    TokenBucket tokenBucket;

    @Autowired
    public void setRateLimitInterceptor(TokenBucket tokenBucket) {
        this.tokenBucket = tokenBucket;
    }

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull jakarta.servlet.http.HttpServletResponse response, @NotNull Object handler) throws Exception {
        // 在此处检查请求是否超出了速率限制
        if (!tokenBucket.allowRequest()) {
            // 如果超出了速率限制，可以根据实际情况进行处理，例如返回错误信息或者拒绝请求
            response.setStatus(429); // 请求过多，使用状态码 429
            response.getWriter().write("Too many requests"); // 返回自定义的错误信息
            return false;
        }
        return true;
    }
}


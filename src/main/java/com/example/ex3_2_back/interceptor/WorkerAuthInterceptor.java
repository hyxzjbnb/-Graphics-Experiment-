package com.example.ex3_2_back.interceptor;

import com.example.ex3_2_back.domain.Result;
import com.example.ex3_2_back.service.AdminAuthService;
import com.example.ex3_2_back.utils.MyJwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import java.util.Arrays;
import java.util.Optional;

@Component
@Slf4j
public class WorkerAuthInterceptor implements HandlerInterceptor {

    private AdminAuthService adminAuthService; // 使用 AdminAuthService 来验证和更新令牌

    @Autowired
    public void setAdminAuthService(AdminAuthService adminAuthService) {
        this.adminAuthService = adminAuthService;
    }

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        Result result = adminAuthService.validateAndUpdateToken(request, response);
        if (!result.isSuccess()) {
            // 如果验证失败，则返回未授权状态
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            log.info("Unauthorized access detected: {}", result.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, ModelAndView modelAndView) throws Exception {
        // 没有任何后处理逻辑，可以留空
    }

    @Override
    public void afterCompletion(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, Exception ex) throws Exception {
        // 没有任何完成后操作的逻辑，可以留空
    }
}

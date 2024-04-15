package com.example.ex3_2_back.interceptor;

import com.example.ex3_2_back.repository.WorkerRepository;
import com.example.ex3_2_back.utils.MyJwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class WorkerAuthInterceptor implements HandlerInterceptor {
    @Autowired
    private MyJwtUtil jwtUtil;

    @Autowired
    private WorkerRepository workerRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Remove "Bearer " prefix
            String username = String.valueOf(jwtUtil.decodeToken(token));
            if (username != null && workerRepository.findByName(username).isPresent()) {
                return true; // Continue with the request processing
            }
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Unauthorized access");
        return false; // Stop the execution chain
    }
}

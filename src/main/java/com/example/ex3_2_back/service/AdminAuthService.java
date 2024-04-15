package com.example.ex3_2_back.service;

import com.example.ex3_2_back.domain.Result;
import com.example.ex3_2_back.entity.Worker;
import com.example.ex3_2_back.repository.WorkerRepository;
import com.example.ex3_2_back.utils.MyJwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import java.util.Optional;
@Service
@Slf4j
public class AdminAuthService {
    private WorkerRepository workerRepository;
    private MyJwtUtil jwtUtil;

    private WorkerService workerService;  // 服务用于检查Worker表

    @Autowired
    public void setWorkerRepository(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    @Autowired
    public void setJwtUtil(MyJwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Autowired
    public void setWorkerService(WorkerService workerService) {
        this.workerService = workerService;
    }

    public Result login(String username, String password, HttpServletResponse response) {
        if (!workerRepository.existsByNameAndPassword(username, password)) {
            return Result.error("Invalid username or password");
        }

        setTokenCookie(username, response);
        return Result.success("Logged in successfully");
    }

    @NotNull
    public Result validateAndUpdateToken(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response) {
        var cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            var message = String.format("%s: No token in cookie", request.getRequestURL());
            log.info(message);
            return Result.error(message);
        }

        Optional<Cookie> optionalCookie = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("token"))
                .findAny();
        String token = optionalCookie.map(Cookie::getValue).orElse("");

        var optionalUsername = jwtUtil.decodeToken(token);
        if (optionalUsername.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            var message = String.format("%s: No username in token", request.getRequestURL());
            log.info(message);
            return Result.error(message);
        }

        var username = optionalUsername.get();
        if (!workerRepository.existsByName(username)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            var message = String.format("%s: User not authorized as worker", request.getRequestURL());
            log.info(message);
            return Result.error(message);
        }

        request.setAttribute("username", username);
        setTokenCookie(username, response);

        return Result.success();
    }

    /**
     * Set or refresh the JWT cookie for the authenticated worker.
     *
     * @param username Username of the authenticated worker
     * @param response HTTP response to modify
     */
    private void setTokenCookie(String username, @NotNull HttpServletResponse response) {
        String token = jwtUtil.createToken(username);
        var cookie = new Cookie("token", token);
        cookie.setPath("/");
        cookie.setMaxAge(30000000); // Set a long expiry time
        response.addCookie(cookie);
        response.addHeader("token", token);
        response.addHeader("username", username);
    }
}

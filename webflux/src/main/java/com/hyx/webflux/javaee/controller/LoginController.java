package com.hyx.webflux.javaee.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyx.webflux.javaee.domain.auth.RegisterDomain;
import com.hyx.webflux.javaee.model.HttpResult;
import com.hyx.webflux.javaee.model.LoginResponse;
import com.hyx.webflux.javaee.model.MyUser;
import com.hyx.webflux.javaee.repository.MyUserDetailsRepository;
import com.hyx.webflux.javaee.service.JwtSigner;
import com.hyx.webflux.javaee.service.MyUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author hyxzjbnb
 * @create 2024-05-25-22:35
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
@Slf4j
public class LoginController {

    private final MyUserDetailsRepository myUserRepository;
    private final MyUserService myUserService;
    private final JwtSigner jwtSigner;
    //private final PasswordEncoder password = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @PostMapping("login")
    public Mono<HttpResult> login (@RequestBody Map<String, String> user) {

        ObjectMapper mapper = new ObjectMapper();

        return Mono.just(user.get("username"))
                .flatMap(myUserRepository::findByUsername)
                .doOnNext(i -> log.info("{}", i))
                .filter(it -> user.get("password").equals(it.getPassword()))
                .map(it -> {
                    try {
                        return new HttpResult(HttpStatus.OK.value(),
                                "成功登录",
                                new LoginResponse(it.getUsername(),
                                        mapper.writeValueAsString(it
                                                .getAuthorities()
                                                .stream()
                                                .map(GrantedAuthority::getAuthority)
                                                .collect(Collectors.toList())),
                                        jwtSigner.generateToken(it)));
                    } catch (JsonProcessingException e) {
                        return new HttpResult();
                    }
                })
                .onErrorResume(e -> Mono.empty())
                .switchIfEmpty(Mono.just(new HttpResult(HttpStatus.UNAUTHORIZED.value(), "登录失败", null)));
    }

//    @CrossOrigin
    @PostMapping("signup")
    public Mono<HttpResult> register(@RequestBody RegisterDomain register) {
        MyUser user = new MyUser();
        user.setUsername(register.getUsername());
        user.setAuthorities(register.getAuthorities());
        user.setPassword(register.getPassword());
        return Mono.just(user)
                .flatMap(myUserService::save)  // 使用flatMap来处理异步的保存操作
                .map(it -> new HttpResult(HttpStatus.OK.value(), "注册成功", null))  // 成功注册
                .onErrorResume(e -> {
                    // 日志记录错误
                    log.error("注册失败", e);
                    // 根据不同的错误类型返回不同的HTTP状态码
                    if (e instanceof Exception) {
                        return Mono.just(new HttpResult(HttpStatus.BAD_REQUEST.value(), "请求错误: " + e.getMessage(), null));
                    } else {
                        return Mono.just(new HttpResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "内部服务器错误: " + e.getMessage(), null));
                    }
                });
    }

}

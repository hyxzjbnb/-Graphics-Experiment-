package com.hyx.webflux.javaee.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyx.webflux.javaee.model.HttpResult;
import com.hyx.webflux.javaee.service.JwtSigner;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

/**
 * @author hyxzjbnb
 * @create 2024-05-25-22:35
 */
@Component
@Slf4j
@AllArgsConstructor
public class JwtWebFilter implements WebFilter {

    private final JwtSigner jwtSigner;
    private static final Set<String> IGNORE_PATHS = new HashSet<>();

    static {
        IGNORE_PATHS.add("/auth/login");
        IGNORE_PATHS.add("/auth/signout");
        IGNORE_PATHS.add("/auth/signup");
    }

    protected Mono<Void> writeErrorMessage(ServerHttpResponse response, HttpStatus status, String msg) throws JsonProcessingException, UnsupportedEncodingException {
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper mapper=new ObjectMapper();
        String body = mapper.writeValueAsString(new HttpResult(status.value(), msg, null));
        DataBuffer dataBuffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(dataBuffer));
    }

    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        String path = request.getPath().value();
        // Check if the path is one of the ignored paths
//        if (IGNORE_PATHS.stream().anyMatch(path::contains)) {
//            return chain.filter(exchange);
//        }
//        String auth = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
//        if (auth == null) {
//            return this.writeErrorMessage(response, HttpStatus.NOT_ACCEPTABLE, "没有携带token");
//        }
//        else if (!auth.startsWith(jwtSigner.getTokenPrefix())) {
//            return this.writeErrorMessage(response, HttpStatus.NOT_ACCEPTABLE, "token 没有以" + jwtSigner.getTokenPrefix() + "开始");
//        }
//
//        String token = auth.replace(jwtSigner.getTokenPrefix(),"");
//        try {
//            exchange.getAttributes().put("token", token);
//        } catch (Exception e) {
//            return this.writeErrorMessage(response, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
//        }

        return chain.filter(exchange);
    }
}

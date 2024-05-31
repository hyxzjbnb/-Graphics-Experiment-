package com.hyx.webflux.javaee.handler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyx.webflux.javaee.model.HttpResult;
import com.hyx.webflux.javaee.util.TokenBucket;
import lombok.SneakyThrows;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * @author hyxzjbnb
 * @create 2024-05-25-22:35
 */
@Component
@EnableScheduling
@Order(Ordered.HIGHEST_PRECEDENCE) // Ensure this filter is one of the first to run
public class TokenBucketFilter implements WebFilter {

    private final TokenBucket tokenBucket;

    public TokenBucketFilter(TokenBucket tokenBucket) {
        this.tokenBucket = tokenBucket;
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
        if (path.startsWith("/products")) {
            if (tokenBucket.allowRequest()) {
                return chain.filter(exchange);
            } else {
                return this.writeErrorMessage(response, HttpStatus.NOT_ACCEPTABLE, "请求被限流");
            }
        }else{
            return chain.filter(exchange);
        }
    }

    @Scheduled(fixedRate = 60000) // 每分钟重新填充一次
    public void refillTokens() {
        tokenBucket.refill();
    }
}

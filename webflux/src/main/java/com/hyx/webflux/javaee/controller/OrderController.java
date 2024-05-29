package com.hyx.webflux.javaee.controller;

import com.hyx.webflux.javaee.model.HttpResult;
import com.hyx.webflux.javaee.model.Order;
import com.hyx.webflux.javaee.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public Mono<HttpResult> createOrder(@RequestBody Order order) {
        //log.info("{}",order);
        return Mono.just(order)
                .flatMap(orderService::createOrder)  // 使用flatMap来处理异步的保存操作
                .map(it -> new HttpResult(HttpStatus.OK.value(), "成功", null))  // 成功注册
                .onErrorResume(e -> {
                    // 日志记录错误
                    log.error("失败", e);
                    // 根据不同的错误类型返回不同的HTTP状态码
                    if (e instanceof Exception) {
                        return Mono.just(new HttpResult(HttpStatus.BAD_REQUEST.value(), "请求错误: " + e.getMessage(), null));
                    } else {
                        return Mono.just(new HttpResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "内部服务器错误: " + e.getMessage(), null));
                    }
                });
    }

    @PatchMapping("/{orderId}")
    public Mono<HttpResult> updateOrderStatus(@PathVariable Integer orderId, @RequestParam String status) {
        Order order = new Order();
        order.setId(orderId);
        order.setStatus(status);
        return Mono.just(order)
                .flatMap(orderService::updateOrderStatus)  // 使用flatMap来处理异步的保存操作
                .map(it -> new HttpResult(HttpStatus.OK.value(), "成功", null))  // 成功注册
                .onErrorResume(e -> {
                    // 日志记录错误
                    log.error("失败", e);
                    // 根据不同的错误类型返回不同的HTTP状态码
                    if (e instanceof Exception) {
                        return Mono.just(new HttpResult(HttpStatus.BAD_REQUEST.value(), "请求错误: " + e.getMessage(), null));
                    } else {
                        return Mono.just(new HttpResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "内部服务器错误: " + e.getMessage(), null));
                    }
                });
    }

    @DeleteMapping("/{orderId}")
    public Mono<HttpResult> cancelOrder(@PathVariable Integer orderId) {
        return Mono.just(orderId)
                .flatMap(orderService::cancelOrder)  // 使用flatMap来处理异步的保存操作
                .map(it -> new HttpResult(HttpStatus.OK.value(), "成功", null))  // 成功注册
                .onErrorResume(e -> {
                    // 日志记录错误
                    log.error("失败", e);
                    // 根据不同的错误类型返回不同的HTTP状态码
                    if (e instanceof Exception) {
                        return Mono.just(new HttpResult(HttpStatus.BAD_REQUEST.value(), "请求错误: " + e.getMessage(), null));
                    } else {
                        return Mono.just(new HttpResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "内部服务器错误: " + e.getMessage(), null));
                    }
                });
    }
    @GetMapping
    public Mono<HttpResult> getOrders(@RequestParam(required = false) Integer id,
                                      @RequestParam(required = false) String status) {
        return Mono.fromCallable(() -> {
                    Order order = new Order();
                    order.setId(id);
                    order.setStatus(status);
                    return order;
                })
                .flatMapMany(orderService::getOrders)
                .collectList()
                .map(orders -> new HttpResult(HttpStatus.OK.value(), "成功", orders))
                .onErrorResume(e -> {
                    // 日志记录错误
                    log.error("获取订单失败", e);
                    // 根据不同的错误类型返回不同的HTTP状态码
                    if (e instanceof Exception) {
                        return Mono.just(new HttpResult(HttpStatus.BAD_REQUEST.value(), "请求错误: " + e.getMessage(), null));
                    } else {
                        return Mono.just(new HttpResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "内部服务器错误: " + e.getMessage(), null));
                    }
                });
    }

}

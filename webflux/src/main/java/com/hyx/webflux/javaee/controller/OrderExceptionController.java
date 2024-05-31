package com.hyx.webflux.javaee.controller;

import com.hyx.webflux.javaee.model.OrderException;
import com.hyx.webflux.javaee.service.OrderExceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/order-exception")
public class OrderExceptionController {

    private final OrderExceptionService orderExceptionService;

    @Autowired
    public OrderExceptionController(OrderExceptionService orderExceptionService) {
        this.orderExceptionService = orderExceptionService;
    }

    @PostMapping
    public Mono<OrderException> createOrderException(@RequestParam Integer orderId, @RequestParam String description) {
        return orderExceptionService.createOrderException(orderId, description);
    }

    // Other controller methods
}


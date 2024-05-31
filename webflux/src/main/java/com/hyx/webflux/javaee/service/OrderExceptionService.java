package com.hyx.webflux.javaee.service;

import com.hyx.webflux.javaee.model.OrderException;
import com.hyx.webflux.javaee.repository.OrderExceptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class OrderExceptionService {

    private final OrderExceptionRepository orderExceptionRepository;

    @Autowired
    public OrderExceptionService(OrderExceptionRepository orderExceptionRepository) {
        this.orderExceptionRepository = orderExceptionRepository;
    }

    public Mono<OrderException> createOrderException(Integer orderId, String description) {
        OrderException orderException = OrderException.builder()
                .orderId(orderId)
                .description(description)
                .createdAt(LocalDateTime.now())
                .build();
        return orderExceptionRepository.save(orderException);
    }

    // Other service methods
}

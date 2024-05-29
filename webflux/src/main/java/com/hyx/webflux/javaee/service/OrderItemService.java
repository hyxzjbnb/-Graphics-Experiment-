package com.hyx.webflux.javaee.service;

import com.hyx.webflux.javaee.model.Order;
import com.hyx.webflux.javaee.model.OrderItem;
import com.hyx.webflux.javaee.repository.OrderItemRepository;
import com.hyx.webflux.javaee.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * @author hyxzjbnb
 * @create 2024-05-29-17:21
 */
@Service
@Slf4j
public class OrderItemService {
    @Autowired
    private OrderItemRepository orderitemRepository;

    public Mono<OrderItem> createOrder(OrderItem orderitem) {
        //log.info("{}",order);
        return orderitemRepository.save(orderitem);
    }

}

package com.hyx.webflux.javaee.service;

/**
 * @author hyxzjbnb
 * @create 2024-05-28-16:20
 */
import com.hyx.webflux.javaee.model.Order;
import com.hyx.webflux.javaee.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;

@Service
@Slf4j
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Mono<Order> createOrder(Order order) {
        order.setStatus("Pending");
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        //log.info("{}",order);
        return orderRepository.save(order);
    }

    public Mono<Order> updateOrderStatus(Order order1) {
        Integer orderId = order1.getId();
        String status = order1.getStatus();
        log.info("{}",orderId);
        log.info("{}",status);
        return orderRepository.findById(orderId)
                .flatMap(order -> {
                    order.setStatus(status);
                    order.setUpdatedAt(LocalDateTime.now());
                    return orderRepository.save(order);
                });
    }

    public Mono<Order> cancelOrder(Integer orderId) {
        log.info("{}",orderId);
        return orderRepository.findById(orderId)
                .flatMap(order -> {
                    if (!order.getStatus().equals("Shipped")) {
                        order.setStatus("Cancelled");
                        order.setUpdatedAt(LocalDateTime.now());
                        return orderRepository.save(order);
                    } else {
                        return Mono.error(new IllegalStateException("Cannot cancel a shipped order."));
                    }
                });
    }

    public Flux<Order> getOrders(Order order) {
        Integer customerId = order.getId();
        String status = order.getStatus();
        if (customerId != null && status != null) {
            log.info("1");
            Long l = new Long((long)customerId);
            return orderRepository.findByUidAndStatus(l, status);
        } else if (customerId != null) {
            log.info("2");
            Long l = new Long((long)customerId);
            return orderRepository.findByUid(l);
        } else if (status != "") {
            log.info("3");
            return orderRepository.findByStatus(status);
        } else {
            log.info("4");
            return orderRepository.findAll();
        }
    }
}

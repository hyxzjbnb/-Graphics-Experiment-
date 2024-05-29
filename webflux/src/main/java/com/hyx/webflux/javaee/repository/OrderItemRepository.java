package com.hyx.webflux.javaee.repository;

import com.hyx.webflux.javaee.model.Order;
import com.hyx.webflux.javaee.model.OrderItem;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

/**
 * @author hyxzjbnb
 * @create 2024-05-29-17:29
 */
public interface OrderItemRepository extends ReactiveCrudRepository<OrderItem, Integer> {
}

package com.hyx.webflux.javaee.repository;

/**
 * @author hyxzjbnb
 * @create 2024-05-28-16:18
 */
import com.hyx.webflux.javaee.model.Order;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderRepository extends ReactiveCrudRepository<Order, Integer> {
    Flux<Order> findByUidAndStatus(Long userId, String status);
    Flux<Order> findByUid(Long userId);
    Flux<Order> findByStatus(String status);
}

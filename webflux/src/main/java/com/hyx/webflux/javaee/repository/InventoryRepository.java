package com.hyx.webflux.javaee.repository;

import com.hyx.webflux.javaee.model.Inventory;
import com.hyx.webflux.javaee.model.Order;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * @author hyxzjbnb
 * @create 2024-05-29-16:28
 */
public interface InventoryRepository extends ReactiveCrudRepository<Inventory, Integer> {
    Flux<Inventory> findByPid(Integer productId);
}

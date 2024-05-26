package com.hyx.webflux.javaee.repository;

import com.hyx.webflux.javaee.model.MyUser;
import com.hyx.webflux.javaee.model.Product;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * @author hyxzjbnb
 * @create 2024-05-25-22:35
 */
public interface ProductRepository extends ReactiveCrudRepository<Product, Long> {
    @Operation(summary = "通过产品名查找")
    Mono<Product> findById(int id);
}

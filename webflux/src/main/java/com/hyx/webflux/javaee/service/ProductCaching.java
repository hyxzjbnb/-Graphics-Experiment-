package com.hyx.webflux.javaee.service;

import com.hyx.webflux.javaee.annotation.RedisReactiveCacheAdd;
import com.hyx.webflux.javaee.annotation.RedisReactiveCacheGet;
import com.hyx.webflux.javaee.model.Product;
import com.hyx.webflux.javaee.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @author hyxzjbnb
 * @create 2024-05-26-20:57
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ProductCaching {
    private final ProductRepository productRepository;
    @RedisReactiveCacheGet(key = "#id")
    public Mono<Product> getProductById(int id) {
        return productRepository.findById(id);
    }

    @RedisReactiveCacheAdd(key = "#product.name", useArgsHash = true)
    public Mono<Product> saveProduct(Product product) {
        return productRepository.save(product)
                .doOnSuccess(savedUser -> log.info("保存成功: " + savedUser))
                .doOnError(error -> log.info("保存失败: " + error.getMessage()));
    }
}

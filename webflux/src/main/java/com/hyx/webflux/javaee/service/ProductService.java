package com.hyx.webflux.javaee.service;

import com.hyx.webflux.javaee.annotation.RedisReactiveCacheAdd;
import com.hyx.webflux.javaee.annotation.RedisReactiveCacheEvict;
import com.hyx.webflux.javaee.annotation.RedisReactiveCacheGet;
import com.hyx.webflux.javaee.annotation.RedisReactiveCacheUpdate;
import com.hyx.webflux.javaee.model.MyUser;
import com.hyx.webflux.javaee.model.Product;
import com.hyx.webflux.javaee.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

/**
 * @author hyxzjbnb
 * @create 2024-05-26-19:09
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {


    private final ProductRepository productRepository;

    public Mono<Product> saveProduct(Product user) {
        return productRepository.save(user)
                .doOnSuccess(savedUser -> log.info("保存成功: " + savedUser))
                .doOnError(error -> log.info("保存失败: " + error.getMessage()));
    }

}

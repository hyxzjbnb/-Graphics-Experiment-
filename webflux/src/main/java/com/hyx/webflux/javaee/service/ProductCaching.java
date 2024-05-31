package com.hyx.webflux.javaee.service;

import com.hyx.webflux.javaee.annotation.RedisReactiveCacheAdd;
import com.hyx.webflux.javaee.annotation.RedisReactiveCacheEvict;
import com.hyx.webflux.javaee.annotation.RedisReactiveCacheGet;
import com.hyx.webflux.javaee.annotation.RedisReactiveCacheUpdate;
import com.hyx.webflux.javaee.model.Product;
import com.hyx.webflux.javaee.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * @author hyxzjbnb
 * @create 2024-05-26-20:57
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ProductCaching {

    private final ProductRepository productRepository;
//往缓存里更新

    @RedisReactiveCacheUpdate(key = "#product.name")
    public Mono<Product> updateProduct(Product product) {
        String a = product.getName();
        log.info("{}",a);
        return productRepository.findByName(a)
                .flatMap(product1 ->{product1.setDescription(product.getDescription());
                product1.setPrice(product.getPrice());
                return productRepository.save(product1);});
    }
//同时删数据库和缓存
    @RedisReactiveCacheEvict(key = "#product.name")
    public Mono<Void> deleteProductById(Product product) {
        long id = product.getId();
        return productRepository.deleteById(id)
                .doOnSuccess(unused -> log.info("删除成功: " + id))
                .doOnError(error -> log.info("删除失败: " + error.getMessage()));
    }
//只删缓存
    @RedisReactiveCacheEvict(key = "#product.name")
    public Mono<Void> deleteProductById2(Product product) {
//        long id = product.getId();
//        return productRepository.deleteById(id)
//                .doOnSuccess(unused -> log.info("删除成功: " + id))
//                .doOnError(error -> log.info("删除失败: " + error.getMessage()));
        return Mono.empty();
    }


    @RedisReactiveCacheGet(key = "#product.name")
    public Mono<Product> getProductById(Product product) {
        int id = product.getId();
        return productRepository.findById(id);
    }
//保存到缓存里
    @RedisReactiveCacheAdd(key = "#product.name")
    public Mono<Product> saveProduct(Product product) {
        return productRepository.save(product)
                .doOnSuccess(savedUser -> log.info("保存成功: " + savedUser))
                .doOnError(error -> log.info("保存失败: " + error.getMessage()));
    }

}

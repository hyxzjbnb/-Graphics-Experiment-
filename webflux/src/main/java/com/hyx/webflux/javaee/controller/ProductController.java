package com.hyx.webflux.javaee.controller;

import com.hyx.webflux.javaee.domain.Result;
import com.hyx.webflux.javaee.domain.auth.ProductDemo;
import com.hyx.webflux.javaee.model.HttpResult;
import com.hyx.webflux.javaee.model.Product;
import com.hyx.webflux.javaee.service.ProductCaching;
import com.hyx.webflux.javaee.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

/**
 * @author hyxzjbnb
 * @create 2024-05-26-19:25
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCaching productCaching;

    @PostMapping
    //@Operation(summary = "添加产品信息", description = "添加新的产品信息到数据库，包括名称、描述和初始库存量。")
    public Mono<HttpResult> addProduct(@RequestBody ProductDemo demo) {
        Product product = new Product();
        product.setName(demo.getName());
        product.setDescription(demo.getDescription());
        product.setPrice(demo.getPrice());
        product.setDate(LocalDate.now());
        log.info("{}",product);
        return Mono.just(product)
                .flatMap(productService::saveProduct)  // 使用flatMap来处理异步的保存操作
                .map(it -> new HttpResult(HttpStatus.OK.value(), "成功", null))  // 成功注册
                .onErrorResume(e -> {
                    // 日志记录错误
                    log.error("失败", e);
                    // 根据不同的错误类型返回不同的HTTP状态码
                    if (e instanceof Exception) {
                        return Mono.just(new HttpResult(HttpStatus.BAD_REQUEST.value(), "请求错误: " + e.getMessage(), null));
                    } else {
                        return Mono.just(new HttpResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "内部服务器错误: " + e.getMessage(), null));
                    }
                });
    }

    @PostMapping("caching")
    //@Operation(summary = "添加产品信息", description = "添加新的产品信息到数据库，包括名称、描述和初始库存量。")
    public Mono<HttpResult> addProductcaching(@RequestBody ProductDemo demo) {
        Product product = new Product();
        product.setName(demo.getName());
        product.setDescription(demo.getDescription());
        product.setPrice(demo.getPrice());
        product.setDate(LocalDate.now());
        log.info("{}",product);
        return Mono.just(product)
                .flatMap(productCaching::saveProduct)  // 使用flatMap来处理异步的保存操作
                .map(it -> new HttpResult(HttpStatus.OK.value(), "成功", null))  // 成功注册
                .onErrorResume(e -> {
                    // 日志记录错误
                    log.error("失败", e);
                    // 根据不同的错误类型返回不同的HTTP状态码
                    if (e instanceof Exception) {
                        return Mono.just(new HttpResult(HttpStatus.BAD_REQUEST.value(), "请求错误: " + e.getMessage(), null));
                    } else {
                        return Mono.just(new HttpResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "内部服务器错误: " + e.getMessage(), null));
                    }
                });
    }

}

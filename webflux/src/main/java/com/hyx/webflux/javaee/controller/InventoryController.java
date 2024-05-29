package com.hyx.webflux.javaee.controller;

import com.hyx.webflux.javaee.domain.auth.InventoryDemo;
import com.hyx.webflux.javaee.model.HttpResult;
import com.hyx.webflux.javaee.model.Inventory;
import com.hyx.webflux.javaee.model.Order;
import com.hyx.webflux.javaee.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author hyxzjbnb
 * @create 2024-05-29-16:40
 */
@RestController
@Slf4j
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    //保存库存
    @PostMapping
    public Mono<HttpResult> createInventory(@RequestBody InventoryDemo inventory1) {
        log.info("{}",inventory1);
        Inventory inventory = new Inventory();
        inventory.setQuantity(inventory1.getQ());
        inventory.setPid(inventory1.getPid());
        //inventory.setId(1);
        inventory.setDate(LocalDateTime.now());
        log.info("{}",inventory);
        return Mono.just(inventory)
                .flatMap(inventoryService::createInventory)  // 使用flatMap来处理异步的保存操作
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

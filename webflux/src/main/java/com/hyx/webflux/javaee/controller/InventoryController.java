package com.hyx.webflux.javaee.controller;

import com.hyx.webflux.javaee.domain.Result;
import com.hyx.webflux.javaee.domain.auth.InventoryDemo;
import com.hyx.webflux.javaee.model.HttpResult;
import com.hyx.webflux.javaee.model.Inventory;
import com.hyx.webflux.javaee.model.Order;
import com.hyx.webflux.javaee.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
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

    //更新库存
    @PatchMapping("/{productId}")
    @Operation(summary = "更新库存", description = "更新指定产品的库存数量。")
    public Mono<HttpResult>  updateInventory(@PathVariable Integer productId, @RequestParam int changeInQuantity) {
        Inventory inventory = new Inventory();
        inventory.setPid(productId);
        inventory.setQuantity(changeInQuantity);
        //log.info("{}",inventory);
        return Mono.just(inventory)
                .flatMap(inventoryService::updateInventory)  // 使用flatMap来处理异步的保存操作
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

    @GetMapping
    @Operation(summary = "查询库存", description = "查询特定产品的当前库存量。")
    public Flux<HttpResult> getInventory(@RequestParam Integer productId) {
        return inventoryService.getInventoryQuantity(productId)
                .map(quantity -> HttpResult.success("查询成功", quantity))
                .onErrorResume(e -> Mono.just(HttpResult.error("查询失败", e.getMessage())));
    }

    //删除指定数据

    @DeleteMapping
    @Operation(summary = "删除库存", description = "删除特定产品的库存记录。")
    public Mono<HttpResult> deleteInventory(@RequestParam Integer productId) {
        return inventoryService.deleteInventory(productId)
                .then(Mono.just(HttpResult.success("删除成功", null)))
                .onErrorResume(e -> Mono.just(HttpResult.error("删除失败", e.getMessage())));
    }
}

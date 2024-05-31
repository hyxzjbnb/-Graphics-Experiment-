package com.hyx.webflux.javaee.controller;

import com.hyx.webflux.javaee.domain.Result;
import com.hyx.webflux.javaee.domain.auth.Order1;
import com.hyx.webflux.javaee.model.HttpResult;
import com.hyx.webflux.javaee.service.OrderItemService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import com.hyx.webflux.javaee.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * @author hyxzjbnb
 * @create 2024-05-29-17:19
 */
@RestController
@Slf4j
@RequestMapping("/order-items")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @PostMapping
    @Operation(summary = "添加商品到订单", description = "为特定订单添加商品。")
    public Mono<HttpResult> addOrderItem(@RequestBody Order1 orderItem1) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOid(orderItem1.getOid());
        orderItem.setPid(orderItem1.getPid());
        orderItem.setQuantity(orderItem1.getQ());
        log.info("{}",orderItem);
        return Mono.just(orderItem)
                .flatMap(orderItemService::createOrder)  // 使用flatMap来处理异步的保存操作
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
    //更新
    @PatchMapping("/{orderItemId}")
    @Operation(summary = "更新订单商品", description = "更新订单中某个商品的数量。")
    public Mono<HttpResult> updateOrderItem(@PathVariable Integer orderItemId, @RequestParam Integer newQuantity) {
        return orderItemService.updateOrderItem(orderItemId, newQuantity)
                .map(updatedOrderItem -> HttpResult.success("更新成功", updatedOrderItem))
                .onErrorResume(e -> Mono.just(HttpResult.error("更新失败: " + e.getMessage(), e)));
    }

    @DeleteMapping("/{orderItemId}")
    @Operation(summary = "删除订单商品", description = "从订单中删除一个商品，并更新库存。")
    public Mono<HttpResult> removeOrderItem(@PathVariable Integer orderItemId) {
        return orderItemService.removeOrderItem(orderItemId)
                .then(Mono.just(HttpResult.success("删除成功", null)))
                .onErrorResume(e -> Mono.just(HttpResult.error("删除失败: " + e.getMessage(), e)));
    }
    //删除

    @GetMapping("/{orderItemId}")
    @Operation(summary = "获取订单商品信息", description = "获取订单中某个商品的信息。")
    public Mono<HttpResult> getOrderItem(@PathVariable Integer orderItemId) {
        return orderItemService.getOrderItem(orderItemId)
                .map(orderItem -> HttpResult.success("查询成功", orderItem))
                .onErrorResume(e -> Mono.just(HttpResult.error("查询失败: " + e.getMessage(), e)));
    }


}

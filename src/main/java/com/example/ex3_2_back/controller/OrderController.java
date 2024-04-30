package com.example.ex3_2_back.controller;

import com.example.ex3_2_back.domain.Result;
import com.example.ex3_2_back.entity.Order;
import com.example.ex3_2_back.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/orders")
@Tag(name = "订单管理", description = "订单管理接口")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    @Operation(summary = "创建订单", description = "创建一个新订单，同时进行库存检查和用户验证。")
    public Result createOrder(@RequestBody Order order) {
        try {
            Order createdOrder = orderService.createOrder(order);
            return Result.success(createdOrder);
        } catch (Exception e) {
            return Result.error(e.getMessage()).addErrors(e);
        }
    }

    @PatchMapping("/{orderId}")
    @Operation(summary = "更新订单状态", description = "更新订单的状态。")
    public Result updateOrderStatus(@PathVariable Integer orderId, @RequestParam String status) {
        try {
            Order updatedOrder = orderService.updateOrderStatus(orderId, status);
            return Result.success(updatedOrder);
        } catch (Exception e) {
            return Result.error(e.getMessage()).addErrors(e);
        }
    }

    @DeleteMapping("/{orderId}")
    @Operation(summary = "取消订单", description = "取消订单，并回滚库存。")
    public Result cancelOrder(@PathVariable Integer orderId) {
        try {
            orderService.cancelOrder(orderId);
            return Result.success("Order cancelled successfully.");
        } catch (IllegalStateException ise) {
            return Result.error(ise.getMessage());
        } catch (Exception e) {
            return Result.error(e.getMessage()).addErrors(e);
        }
    }

    @GetMapping
    @Operation(summary = "查询订单", description = "根据用户或状态查询订单。")
    public Result getOrders(@RequestParam(required = false) Integer userId,
                            @RequestParam(required = false) String status) {
        try {
            // The getOrder method in the service would need to handle filtering by user and status
            // This is a placeholder for how you might call such a method
            return Result.success(orderService.getOrders(userId, status));
        } catch (Exception e) {
            return Result.error(e.getMessage()).addErrors(e);
        }
    }
}

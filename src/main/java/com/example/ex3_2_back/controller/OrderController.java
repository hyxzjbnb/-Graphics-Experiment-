package com.example.ex3_2_back.controller;

import com.example.ex3_2_back.domain.Result;
import com.example.ex3_2_back.entity.Order;
import com.example.ex3_2_back.repository.OrderRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping
    @Operation(summary = "查询所有订单", description = "查询所有订单")
    public Result all() {
        List<Order> orders = orderRepository.findAll();
        return Result.success(orders);
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "查询单个订单", description = "查询单个订单")
    public Result one(@PathVariable Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + orderId));
        return Result.success(order);
    }

    @PostMapping
    @Operation(summary = "创建订单", description = "创建订单")
    public Result create(@RequestBody Order order) {
        Order createdOrder = orderRepository.save(order);
        return Result.success(createdOrder);
    }

    @DeleteMapping("/{orderId}")
    @Operation(summary = "删除订单", description = "删除订单")
    public Result delete(@PathVariable Long orderId) {
        orderRepository.deleteById(orderId);
        return Result.success();
    }
}

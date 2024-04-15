package com.example.ex3_2_back.controller;

import com.example.ex3_2_back.domain.Result;
import com.example.ex3_2_back.entity.OrderItem;
import com.example.ex3_2_back.service.OrderItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order-items")
@Tag(name = "OrderItemController", description = "订单商品管理接口")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @PostMapping
    @Operation(summary = "添加商品到订单", description = "为特定订单添加商品。")
    public Result addOrderItem(@RequestBody OrderItem orderItem) {
        try {
            OrderItem createdOrderItem = orderItemService.addOrderItem(orderItem);
            return Result.success(createdOrderItem);
        } catch (Exception e) {
            return Result.error(e.getMessage()).addErrors(e);
        }
    }

    @PatchMapping("/{orderItemId}")
    @Operation(summary = "更新订单商品", description = "更新订单中某个商品的数量。")
    public Result updateOrderItem(@PathVariable Integer orderItemId, @RequestParam Integer newQuantity) {
        try {
            OrderItem updatedOrderItem = orderItemService.updateOrderItem(orderItemId, newQuantity);
            return Result.success(updatedOrderItem);
        } catch (Exception e) {
            return Result.error(e.getMessage()).addErrors(e);
        }
    }

    @DeleteMapping("/{orderItemId}")
    @Operation(summary = "删除订单商品", description = "从订单中删除一个商品，并更新库存。")
    public Result removeOrderItem(@PathVariable Integer orderItemId) {
        try {
            orderItemService.removeOrderItem(orderItemId);
            return Result.success("Order item removed successfully.");
        } catch (Exception e) {
            return Result.error(e.getMessage()).addErrors(e);
        }
    }
}

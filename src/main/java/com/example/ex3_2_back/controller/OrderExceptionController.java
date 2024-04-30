package com.example.ex3_2_back.controller;

import com.example.ex3_2_back.domain.Result;
import com.example.ex3_2_back.entity.OrderException;
import com.example.ex3_2_back.service.OrderExceptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@Slf4j
@RequestMapping("/order-exceptions")
@Tag(name = "订单异常管理", description = "订单异常管理接口")
public class OrderExceptionController {

    @Autowired
    private OrderExceptionService orderExceptionService;

    @PostMapping
    @Operation(summary = "记录异常", description = "记录订单处理中的异常。")
    public Result recordException(@RequestParam Integer orderId, @RequestParam String description) {
        try {
            OrderException exception = orderExceptionService.recordException(orderId, description);
            return Result.success(exception);
        } catch (Exception e) {
            return Result.error(e.getMessage()).addErrors(e);
        }
    }

    @PatchMapping("/{exceptionId}")
    @Operation(summary = "更新异常记录", description = "更新异常描述或状态。")
    public Result updateException(@PathVariable Integer exceptionId, @RequestParam String newDescription) {
        try {
            OrderException updatedException = orderExceptionService.updateException(exceptionId, newDescription);
            return Result.success(updatedException);
        } catch (Exception e) {
            return Result.error(e.getMessage()).addErrors(e);
        }
    }

    @GetMapping
    @Operation(summary = "查询异常", description = "按订单ID或日期查询异常记录。")
    public Result getExceptions(@RequestParam(required = false) Integer orderId,
                                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            // Assume getExceptions is implemented in the service to handle the logic for filtering by orderId and/or date
            return Result.success(orderExceptionService.getExceptions(orderId, date));
        } catch (Exception e) {
            return Result.error(e.getMessage()).addErrors(e);
        }
    }
}

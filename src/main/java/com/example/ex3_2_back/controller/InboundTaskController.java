package com.example.ex3_2_back.controller;

/**
 * @author hyxzjbnb
 * @create 2024-04-29-18:27
 */
import com.example.ex3_2_back.entity.InboundTask;
import com.example.ex3_2_back.service.InboundTaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.ex3_2_back.domain.Result;
@RestController
@RequestMapping("/inbound-tasks")
@Tag(name = "InboundTaskController", description = "入库任务管理接口")
public class InboundTaskController {

    @Autowired
    private InboundTaskService inboundTaskService;

    @PostMapping
    @Operation(summary = "创建入库任务", description = "创建一个新的入库任务。")
    public Result createInboundTask() {
        try {
            InboundTask createdTask = inboundTaskService.createInboundTask();
            return Result.success(createdTask);
        } catch (Exception e) {
            return Result.error(e.getMessage()).addErrors(e);
        }
    }

    @PatchMapping("/{taskId}")
    @Operation(summary = "更新入库任务状态", description = "更新入库任务的状态。")
    public Result updateTaskStatus(@PathVariable Integer taskId, @RequestBody String status) {
        try {
            InboundTask updatedTask = inboundTaskService.updateTaskStatus(taskId, status);
            return Result.success(updatedTask);
        } catch (Exception e) {
            return Result.error(e.getMessage()).addErrors(e);
        }
    }

    @DeleteMapping("/{taskId}")
    @Operation(summary = "删除入库任务", description = "删除指定ID的入库任务。")
    public Result deleteTask(@PathVariable Integer taskId) {
        try {
            inboundTaskService.deleteTask(taskId);
            return Result.success("Inbound task deleted successfully.");
        } catch (Exception e) {
            return Result.error(e.getMessage()).addErrors(e);
        }
    }
}


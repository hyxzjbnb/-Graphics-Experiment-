package com.hyx.webflux.javaee.controller;

import com.hyx.webflux.javaee.service.InboundTaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.hyx.webflux.javaee.domain.Result;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequestMapping("/inbound-tasks")
@Tag(name = "入库任务管理", description = "入库任务管理接口")
public class InboundTaskController {

    @Autowired
    private InboundTaskService inboundTaskService;

    @PostMapping
    @Operation(summary = "创建入库任务", description = "创建一个新的入库任务。")
    public Mono<Result> createInboundTask() {
        return inboundTaskService.createInboundTask()
                .map(Result::success)
                .onErrorResume(e -> {
                    log.error("Error creating inbound task", e);
                    return Mono.just(Result.error(e.getMessage()).addErrors(e.getMessage()));
                });
    }

    @PatchMapping("/{taskId}")
    @Operation(summary = "更新入库任务状态", description = "更新入库任务的状态。")
    public Mono<Result> updateTaskStatus(@PathVariable Integer taskId, @RequestParam String status) {
        return inboundTaskService.updateTaskStatus(taskId, status)
                .map(Result::success)
                .onErrorResume(e -> {
                    log.error("Error updating inbound task status", e);
                    return Mono.just(Result.error(e.getMessage()).addErrors(e.getMessage()));
                });
    }

    @DeleteMapping("/{taskId}")
    @Operation(summary = "删除入库任务", description = "删除指定ID的入库任务。")
    public Mono<Result> deleteTask(@PathVariable Integer taskId) {
        return inboundTaskService.deleteTask(taskId)
                .thenReturn(Result.success("Inbound task deleted successfully."))
                .onErrorResume(e -> {
                    log.error("Error deleting inbound task", e);
                    return Mono.just(Result.error(e.getMessage()).addErrors(e.getMessage()));
                });
    }
}

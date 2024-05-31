package com.hyx.webflux.javaee.controller;

import com.hyx.webflux.javaee.service.OutboundTaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import com.hyx.webflux.javaee.domain.Result;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequestMapping("/outbound-tasks")
@Tag(name = "Outbound Task Management", description = "Outbound Task Management API")
public class OutboundTaskController {

    @Autowired
    private OutboundTaskService outboundTaskService;

    @PostMapping
    @Operation(summary = "Create Outbound Task", description = "Create an outbound task for an order.")
    public Mono<Result> createOutboundTask(@RequestParam Integer orderId) {
        return outboundTaskService.createOutboundTask(orderId)
                .map(Result::success)
                .onErrorResume(e -> {
                    log.error("Error creating outbound task", e);
                    return Mono.just(Result.error(e.getMessage()));
                });
    }

    @PatchMapping("/{taskId}")
    @Operation(summary = "Update Outbound Task Status", description = "Update the status of an outbound task.")
    public Mono<Result> updateTaskStatus(@PathVariable Integer taskId, @RequestParam String status) {
        return outboundTaskService.updateTaskStatus(taskId, status)
                .map(Result::success)
                .onErrorResume(e -> {
                    log.error("Error updating task status", e);
                    return Mono.just(Result.error(e.getMessage()));
                });
    }

    @GetMapping
    @Operation(summary = "Search Outbound Tasks", description = "Search outbound tasks by status or date range.")
    public Mono<Result> getOutboundTasks(@RequestParam(required = false) String status,
                                         @RequestParam(required = false) String startDate,
                                         @RequestParam(required = false) String endDate,
                                         @RequestParam(required = false, defaultValue = "0") int page,
                                         @RequestParam(required = false, defaultValue = "10") int size,
                                         @RequestParam(required = false) String sort1,
                                         @RequestParam(required = false) String sort2) {
        try {
            Sort sort = Sort.unsorted();
            if (sort1 != null && !sort1.isEmpty()) {
                Sort.Direction direction = Sort.Direction.ASC; // Default ascending
                if (sort2 != null && sort2.equalsIgnoreCase("desc")) {
                    direction = Sort.Direction.DESC; // Specified descending
                }
                sort = Sort.by(direction, sort1);
            }

            Pageable pageable = PageRequest.of(page, size, sort);
            return outboundTaskService.searchOutPosts(status, startDate, endDate, pageable)
                    .map(Result::success)
                    .onErrorResume(e -> {
                        log.error("Error fetching outbound tasks", e);
                        return Mono.just(Result.error(e.getMessage()));
                    });
        } catch (Exception e) {
            log.error("Error processing request", e);
            return Mono.just(Result.error(e.getMessage()));
        }
    }
}

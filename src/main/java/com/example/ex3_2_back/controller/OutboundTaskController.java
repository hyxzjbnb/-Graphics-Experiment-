package com.example.ex3_2_back.controller;

/**
 * @author hyxzjbnb
 * @create 2024-04-29-19:22
 */
import com.example.ex3_2_back.entity.OutboundTask;
import com.example.ex3_2_back.service.OutboundTaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import com.example.ex3_2_back.domain.Result;
import java.util.List;

@RestController
@RequestMapping("/outbound-tasks")
@Tag(name = "OutboundTaskController", description = "出库任务管理接口")
public class OutboundTaskController {

    @Autowired
    private OutboundTaskService outboundTaskService;

    @PostMapping
    @Operation(summary = "创建出库任务", description = "为订单创建出库任务。")
    public Result createOutboundTask(@RequestParam Integer orderId) {
        try {
            OutboundTask createdTask = outboundTaskService.createOutboundTask(orderId);
            return Result.success(createdTask);
        } catch (Exception e) {
            return Result.error(e.getMessage()).addErrors(e);
        }
    }

    @PatchMapping("/{taskId}")
    @Operation(summary = "更新出库任务状态", description = "更新出库任务的状态。")
    public Result updateTaskStatus(@PathVariable Integer taskId, @RequestParam String status) {
        try {
            OutboundTask updatedTask = outboundTaskService.updateTaskStatus(taskId, status);
            return Result.success(updatedTask);
        } catch (Exception e) {
            return Result.error(e.getMessage()).addErrors(e);
        }
    }

    @GetMapping
    @Operation(summary = "查询出库任务", description = "按状态或日期范围查询出库任务。")
    public Result getOutboundTasks(@RequestParam(required = false) String status,
                                   @RequestParam(required = false) String startDate,
                                   @RequestParam(required = false) String endDate,
                                   @RequestParam(required = false) int page,
                                   @RequestParam(required = false) int size,
                                   @RequestParam(required = false) String sort1,
                                   @RequestParam(required = false) String sort2
    ) {
        try {
            // 创建排序方式
            Sort sort = Sort.unsorted();
            if (sort1 != null && !sort1.isEmpty()) {
                Sort.Direction direction = Sort.Direction.ASC; // 默认升序
                if (sort2 != null && sort2.equalsIgnoreCase("desc")) {
                    direction = Sort.Direction.DESC; // 指定为降序
                }
                sort = Sort.by(direction, sort1);
            }

            // 创建Pageable对象
            Pageable pageable = PageRequest.of(page, size, sort);
            // Implement your logic to fetch tasks based on status, startDate, and endDate
            return outboundTaskService.searchOutPosts(status, startDate, endDate,pageable);
        } catch (Exception e) {
            return Result.error(e.getMessage()).addErrors(e);
        }
    }
}


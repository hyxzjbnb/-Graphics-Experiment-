package com.example.ex3_2_back.controller;

import com.example.ex3_2_back.domain.Result;
import com.example.ex3_2_back.entity.InboundTask;
import com.example.ex3_2_back.entity.OutboundTask;
import com.example.ex3_2_back.repository.InboundTaskRepository;
import com.example.ex3_2_back.repository.OutboundTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InboundTaskRepository inboundTaskRepository;
    private final OutboundTaskRepository outboundTaskRepository;

    @Autowired
    public InventoryController(InboundTaskRepository inboundTaskRepository, OutboundTaskRepository outboundTaskRepository) {
        this.inboundTaskRepository = inboundTaskRepository;
        this.outboundTaskRepository = outboundTaskRepository;
    }

    @PostMapping("/inbound-tasks")
    @Operation(summary = "创建入库任务", description = "创建新的入库任务")
    public Result createInboundTask(@RequestBody InboundTask inboundTask) {
        InboundTask createdTask = inboundTaskRepository.save(inboundTask);
        return Result.success(createdTask);
    }

    @PostMapping("/outbound-tasks")
    @Operation(summary = "创建出库任务", description = "创建新的出库任务")
    public Result createOutboundTask(@RequestBody OutboundTask outboundTask) {
        OutboundTask createdTask = outboundTaskRepository.save(outboundTask);
        return Result.success(createdTask);
    }
}

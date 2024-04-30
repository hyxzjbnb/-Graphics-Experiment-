package com.example.ex3_2_back.service;

import com.example.ex3_2_back.repository.OrderRepository;
import com.example.ex3_2_back.repository.OutboundTaskRepository;
import com.example.ex3_2_back.entity.*;
import com.example.ex3_2_back.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.ex3_2_back.specifications.OutPostSpecifications;
import com.example.ex3_2_back.entity.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.*;
import com.example.ex3_2_back.domain.Result;
/**
 * @author hyxzjbnb
 * @create 2024-04-15-1:19
 */
@Service
@Slf4j
public class OutboundTaskService {

    @Autowired
    private OutboundTaskRepository outboundTaskRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public OutboundTask createOutboundTask(Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        OutboundTask task = new OutboundTask();
        task.setOrder(order);
        task.setStatus("Pending");
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        return outboundTaskRepository.save(task);
    }

    @Transactional
    public OutboundTask updateTaskStatus(Integer taskId, String status) {
        OutboundTask task = outboundTaskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("OutboundTask not found"));
        task.setStatus(status);
        task.setUpdatedAt(LocalDateTime.now());
        return outboundTaskRepository.save(task);
    }

    @Transactional
    public void cancelOutboundTask(Integer taskId) {
        OutboundTask task = outboundTaskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("OutboundTask not found"));
        if (!task.getStatus().equals("Completed")) {
            task.setStatus("Cancelled");
            outboundTaskRepository.save(task);
        } else {
            throw new IllegalStateException("Cannot cancel a completed task.");
        }
    }
    private final ExecutorService executor = Executors.newCachedThreadPool();
    @Transactional(readOnly = true)
    public Result searchOutPosts(String status, String startDate, String endDate, Pageable pageable) {
        Callable<Result> task = () -> {
            try {
                Specification<OutboundTask> spec = Specification.where(null);

                if (status != null && !status.isEmpty()) {
                    spec = spec.and(OutPostSpecifications.hasStatus(status));
                }
                if (startDate != null && !startDate.isEmpty()) {
                    spec = spec.and(OutPostSpecifications.hasStartDate(LocalDateTime.parse(startDate)));
                }
                if (endDate != null && !endDate.isEmpty()) {
                    spec = spec.and(OutPostSpecifications.hasEndDate(LocalDateTime.parse(endDate)));
                }

                Page<OutboundTask> outboundTasks = outboundTaskRepository.findAll(spec, pageable);
                if (outboundTasks.hasContent()) {
                    return Result.success(outboundTasks);
                } else {
                    return Result.error("没有查到相关资料");
                }
            } catch (Exception e) {
                return Result.error("失败：" + e.getMessage());
            }
        };
        Future<Result> future = executor.submit(task);
        try {
            return future.get(10, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            future.cancel(true);
            return Result.error("操作超时，已取消恢复操作");
        } catch (InterruptedException | ExecutionException e) {
            return Result.error("恢复操作中断或执行失败：" + e.getMessage());
        }
    }
}

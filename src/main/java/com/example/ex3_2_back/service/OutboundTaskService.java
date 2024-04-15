package com.example.ex3_2_back.service;

import com.example.ex3_2_back.repository.OrderRepository;
import com.example.ex3_2_back.repository.OutboundTaskRepository;
import com.example.ex3_2_back.entity.*;
import com.example.ex3_2_back.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.ex3_2_back.entity.*;
import java.time.LocalDateTime;
/**
 * @author hyxzjbnb
 * @create 2024-04-15-1:19
 */
@Service
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
}

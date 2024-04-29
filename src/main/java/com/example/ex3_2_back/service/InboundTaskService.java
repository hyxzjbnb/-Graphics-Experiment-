package com.example.ex3_2_back.service;

import com.example.ex3_2_back.entity.InboundTask;
import com.example.ex3_2_back.repository.InboundTaskRepository;
import com.example.ex3_2_back.entity.*;
import com.example.ex3_2_back.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.ex3_2_back.entity.*;
import java.time.LocalDateTime;

/**
 * @author hyxzjbnb
 * @create 2024-04-15-1:17
 */
@Service
@Slf4j
public class InboundTaskService {

    @Autowired
    private InboundTaskRepository inboundTaskRepository;

    @Transactional
    public InboundTask createInboundTask() {
        InboundTask task = new InboundTask();
        task.setStatus("Pending");
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        return inboundTaskRepository.save(task);
    }

    @Transactional
    public InboundTask updateTaskStatus(Integer taskId, String status) {
        InboundTask task = inboundTaskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("InboundTask not found"));
        task.setStatus(status);
        task.setUpdatedAt(LocalDateTime.now());
        return inboundTaskRepository.save(task);
    }

    @Transactional
    public void deleteTask(Integer taskId) {
        InboundTask task = inboundTaskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("InboundTask not found"));
        inboundTaskRepository.delete(task);
    }
}

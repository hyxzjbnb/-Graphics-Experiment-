package com.hyx.webflux.javaee.service;

import com.hyx.webflux.javaee.model.InboundTask;
import com.hyx.webflux.javaee.repository.InboundTaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@Slf4j
public class InboundTaskService {

    @Autowired
    private InboundTaskRepository inboundTaskRepository;

    public Mono<InboundTask> createInboundTask() {
        InboundTask task = new InboundTask();
        task.setStatus("Pending");
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        return inboundTaskRepository.save(task)
                .doOnSuccess(savedTask -> log.info("Created InboundTask: {}", savedTask))
                .doOnError(error -> log.error("Error creating InboundTask: {}", error.getMessage()));
    }

    public Mono<InboundTask> updateTaskStatus(Integer taskId, String status) {
        return inboundTaskRepository.findById(taskId)
                .switchIfEmpty(Mono.error(new RuntimeException("InboundTask not found")))
                .flatMap(task -> {
                    task.setStatus(status);
                    task.setUpdatedAt(LocalDateTime.now());
                    return inboundTaskRepository.save(task)
                            .doOnSuccess(updatedTask -> log.info("Updated InboundTask: {}", updatedTask))
                            .doOnError(error -> log.error("Error updating InboundTask: {}", error.getMessage()));
                });
    }

    public Mono<Void> deleteTask(Integer taskId) {
        return inboundTaskRepository.findById(taskId)
                .switchIfEmpty(Mono.error(new RuntimeException("InboundTask not found")))
                .flatMap(task -> inboundTaskRepository.delete(task)
                        .doOnSuccess(v -> log.info("Deleted InboundTask with ID: {}", taskId))
                        .doOnError(error -> log.error("Error deleting InboundTask: {}", error.getMessage()))
                );
    }
}

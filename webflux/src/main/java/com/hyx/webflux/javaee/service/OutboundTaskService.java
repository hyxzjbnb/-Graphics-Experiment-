package com.hyx.webflux.javaee.service;

import com.hyx.webflux.javaee.domain.Result;
import com.hyx.webflux.javaee.model.OutboundTask;
import com.hyx.webflux.javaee.model.Order;
import com.hyx.webflux.javaee.repository.OutboundTaskRepository;
import com.hyx.webflux.javaee.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OutboundTaskService {

    @Autowired
    private OutboundTaskRepository outboundTaskRepository;
    @Autowired
    private OrderRepository orderRepository;

    public Mono<OutboundTask> createOutboundTask(Integer orderId) {
        return orderRepository.findById(orderId)
                .switchIfEmpty(Mono.error(new RuntimeException("Order not found")))
                .flatMap(order -> {
                    OutboundTask task = new OutboundTask();
                    task.setOrder(order);
                    task.setStatus("Pending");
                    task.setCreatedAt(LocalDateTime.now());
                    task.setUpdatedAt(LocalDateTime.now());
                    return outboundTaskRepository.save(task);
                });
    }

    public Mono<OutboundTask> updateTaskStatus(Integer taskId, String status) {
        return outboundTaskRepository.findById(taskId)
                .switchIfEmpty(Mono.error(new RuntimeException("OutboundTask not found")))
                .flatMap(task -> {
                    task.setStatus(status);
                    task.setUpdatedAt(LocalDateTime.now());
                    return outboundTaskRepository.save(task);
                });
    }

    public Mono<Void> cancelOutboundTask(Integer taskId) {
        return outboundTaskRepository.findById(taskId)
                .switchIfEmpty(Mono.error(new RuntimeException("OutboundTask not found")))
                .flatMap(task -> {
                    if (!task.getStatus().equals("Completed")) {
                        task.setStatus("Cancelled");
                        return outboundTaskRepository.save(task).then();
                    } else {
                        return Mono.error(new IllegalStateException("Cannot cancel a completed task."));
                    }
                });
    }

    public Mono<Result> searchOutPosts(String status, String startDate, String endDate, Pageable pageable) {
        return Mono.defer(() -> {
            Flux<OutboundTask> outboundTasksFlux = outboundTaskRepository.findAll();

            return outboundTasksFlux
                    .collectList()
                    .flatMap(outboundTasks -> {
                        List<OutboundTask> filteredTasks = outboundTasks.stream()
                                .filter(task -> status == null || task.getStatus().equals(status))
                                .filter(task -> {
                                    if (startDate != null) {
                                        try {
                                            return task.getCreatedAt().isAfter(LocalDateTime.parse(startDate));
                                        } catch (Exception e) {
                                            log.error("Error parsing start date", e);
                                            return false;
                                        }
                                    }
                                    return true;
                                })
                                .filter(task -> {
                                    if (endDate != null) {
                                        try {
                                            return task.getCreatedAt().isBefore(LocalDateTime.parse(endDate));
                                        } catch (Exception e) {
                                            log.error("Error parsing end date", e);
                                            return false;
                                        }
                                    }
                                    return true;
                                })
                                .collect(Collectors.toList());

                        if (!filteredTasks.isEmpty()) {
                            return Mono.just(Result.success(filteredTasks));
                        } else {
                            return Mono.just(Result.error("No related data found"));
                        }
                    })
                    .onErrorResume(e -> Mono.just(Result.error("Failure: " + e.getMessage())));
        });
    }
}

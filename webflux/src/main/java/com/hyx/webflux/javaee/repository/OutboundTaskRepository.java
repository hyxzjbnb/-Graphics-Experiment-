package com.hyx.webflux.javaee.repository;

import com.hyx.webflux.javaee.model.OutboundTask;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface OutboundTaskRepository extends ReactiveCrudRepository<OutboundTask, Integer> {
    Flux<OutboundTask> findAll();
}

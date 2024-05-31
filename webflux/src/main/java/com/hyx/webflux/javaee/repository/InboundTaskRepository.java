package com.hyx.webflux.javaee.repository;


import com.hyx.webflux.javaee.model.InboundTask;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InboundTaskRepository extends ReactiveCrudRepository<InboundTask, Integer> {
    // 可以添加更多的查询方法
}


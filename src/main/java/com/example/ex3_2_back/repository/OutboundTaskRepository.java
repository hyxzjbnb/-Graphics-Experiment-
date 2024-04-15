package com.example.ex3_2_back.repository;

import com.example.ex3_2_back.entity.OutboundTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutboundTaskRepository extends JpaRepository<OutboundTask, Integer> {
    // 可以添加更多的查询方法
}

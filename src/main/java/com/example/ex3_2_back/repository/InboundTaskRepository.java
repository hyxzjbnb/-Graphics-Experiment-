package com.example.ex3_2_back.repository;

import com.example.ex3_2_back.entity.InboundTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "InboundTaskRepository")
public interface InboundTaskRepository extends JpaRepository<InboundTask, Long> {
    // 保存入库任务并返回生成的任务ID
    Long saveAndReturnId(InboundTask inboundTask);
}

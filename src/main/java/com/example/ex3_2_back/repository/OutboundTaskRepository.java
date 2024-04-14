package com.example.ex3_2_back.repository;

import com.example.ex3_2_back.entity.OutboundTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "OutboundTaskRepository")
public interface OutboundTaskRepository extends JpaRepository<OutboundTask, Long> {
    // 保存出库任务并返回生成的任务ID
    Long saveAndReturnId(OutboundTask outboundTask);
}

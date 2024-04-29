package com.example.ex3_2_back.repository;

import com.example.ex3_2_back.entity.OutboundTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface OutboundTaskRepository extends JpaRepository<OutboundTask, Integer>, JpaSpecificationExecutor<OutboundTask> {

}

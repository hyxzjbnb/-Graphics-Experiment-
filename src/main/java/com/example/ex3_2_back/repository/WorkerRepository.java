package com.example.ex3_2_back.repository;

import com.example.ex3_2_back.entity.Worker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Optional;

@RepositoryRestResource(path = "WorkerRepository")
@Tag(name = "获取工作人员")
public interface WorkerRepository extends JpaRepository<Worker, Integer> {


}
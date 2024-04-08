package com.example.ex3_2_back.controller;

import com.example.ex3_2_back.domain.Result;
import com.example.ex3_2_back.repository.WorkerRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/worker")
public class WorkerController {
    WorkerRepository workerRepository;

    @Autowired
    @Operation(summary = "setWorkerRepository", description = "setWorkerRepository")
    public void setWorkerRepository(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }
}

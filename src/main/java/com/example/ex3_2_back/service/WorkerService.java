package com.example.ex3_2_back.service;

import com.example.ex3_2_back.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hyxzjbnb
 * @create 2024-04-15-12:31
 */
@Service
@Slf4j
public class WorkerService {

    @Autowired
    private WorkerRepository workerRepository;
}

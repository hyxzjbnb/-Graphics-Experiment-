package com.example.ex3_2_back.service;

import com.example.ex3_2_back.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hyxzjbnb
 * @create 2024-04-15-12:31
 */
@Service
public class WorkerService {

    @Autowired
    private WorkerRepository workerRepository;
}

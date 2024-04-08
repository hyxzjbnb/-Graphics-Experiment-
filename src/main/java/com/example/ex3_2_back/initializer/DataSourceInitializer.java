package com.example.ex3_2_back.initializer;

import com.example.ex3_2_back.entity.*;
import com.example.ex3_2_back.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * 数据库初始化
 */
@Component
public class DataSourceInitializer implements CommandLineRunner {

    UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    WorkerRepository workerRepository;

    @Autowired
    public void setWorkerRepository(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    @Value("${dataInit}")
    boolean enabled = false;

    @Override
    public void run(String... args) throws Exception {

        if (!enabled) {
            return;
        }

        List<User> users = new ArrayList<>();
        var user = User.builder().name("user").password("61259cdf-9cb1-4981-b926-35ebe0906c29").build();
        users.add(user);
        users.add(User.builder().name("Test").password("Test").build());
        users.add(User.builder().name("zzq").password("123").build());

        userRepository.saveAll(users);

        List<Worker> workers = new ArrayList<>();

        var worker = Worker.builder().name("zzq").build();
        workers.add(worker);

        workerRepository.saveAll(workers);


    }


}


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

    @Autowired
    private ProductRepository productRepository;

    // 为 Order Repository 创建依赖注入代码
    OrderRepository orderRepository;
    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // 为 OrderItem Repository 创建依赖注入代码
    OrderItemRepository orderItemRepository;
    @Autowired
    public void setOrderItemRepository(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    // 为 OrderException Repository 创建依赖注入代码
    OrderExceptionRepository orderExceptionRepository;
    @Autowired
    public void setOrderExceptionRepository(OrderExceptionRepository orderExceptionRepository) {
        this.orderExceptionRepository = orderExceptionRepository;
    }

    // 为 InboundTask Repository 创建依赖注入代码
    InboundTaskRepository inboundTaskRepository;
    @Autowired
    public void setInboundTaskRepository(InboundTaskRepository inboundTaskRepository) {
        this.inboundTaskRepository = inboundTaskRepository;
    }

    // 为 InboundItem Repository 创建依赖注入代码
    InboundItemRepository inboundItemRepository;
    @Autowired
    public void setInboundItemRepository(InboundItemRepository inboundItemRepository) {
        this.inboundItemRepository = inboundItemRepository;
    }

    // 为 Inventory Repository 创建依赖注入代码
    InventoryRepository inventoryRepository;
    @Autowired
    public void setInventoryRepository(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    // 为 OutboundTask Repository 创建依赖注入代码
    OutboundTaskRepository outboundTaskRepository;
    @Autowired
    public void setOutboundTaskRepository(OutboundTaskRepository outboundTaskRepository) {
        this.outboundTaskRepository = outboundTaskRepository;
    }

    // 为 OutboundItem Repository 创建依赖注入代码
    OutboundItemRepository outboundItemRepository;
    @Autowired
    public void setOutboundItemRepository(OutboundItemRepository outboundItemRepository) {
        this.outboundItemRepository = outboundItemRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        if (!enabled) {
            return;
        }
        // 初始化用户
        initializeUsers();

        // 初始化工作人员
        initializeWorkers();


    }

    private void initializeUsers() {
        List<User> users = new ArrayList<>();
        users.add(User.builder().name("user").password("61259cdf-9cb1-4981-b926-35ebe0906c29").email("user@example.com").build());
        users.add(User.builder().name("Test").password("Test").email("test@example.com").build());
        users.add(User.builder().name("zzq").password("123").email("zzq@example.com").build());
        userRepository.saveAll(users);
    }

    private void initializeWorkers() {
        List<Worker> workers = new ArrayList<>();
        workers.add(Worker.builder().name("zzq").build());
        workers.add(Worker.builder().name("admin").build());
        workerRepository.saveAll(workers);
    }




}


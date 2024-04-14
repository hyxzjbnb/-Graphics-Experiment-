//package com.example.ex3_2_back.initializer;
//
//import com.example.ex3_2_back.entity.*;
//import com.example.ex3_2_back.repository.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Random;
//import java.util.stream.Collectors;
//
///**
// * 数据库初始化
// */
//@Component
//public class DataSourceInitializer implements CommandLineRunner {
//
//    UserRepository userRepository;
//
//
//    @Autowired
//    public void setUserRepository(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//    WorkerRepository workerRepository;
//
//    @Autowired
//    public void setWorkerRepository(WorkerRepository workerRepository) {
//        this.workerRepository = workerRepository;
//    }
//
//
//
//    @Value("${dataInit}")
//    boolean enabled = false;
//
//    @Override
//    public void run(String... args) throws Exception {
//
//        if (!enabled) {
//            return;
//        }
//
//        List<User> users = new ArrayList<>();
//        var user = User.builder().name("user").password("61259cdf-9cb1-4981-b926-35ebe0906c29").build();
//        users.add(user);
//        users.add(User.builder().name("Test").password("Test").build());
//        users.add(User.builder().name("zzq").password("123").build());
//
//        userRepository.saveAll(users);
//
//        List<Worker> workers = new ArrayList<>();
//
//        var worker = Worker.builder().name("zzq").build();
//        workers.add(worker);
//
//        workerRepository.saveAll(workers);
//
//
//    }
//
//
//}
//
package com.example.ex3_2_back.initializer;

import com.example.ex3_2_back.entity.*;
import com.example.ex3_2_back.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataSourceInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final WorkerRepository workerRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    private final InboundTaskRepository inboundTaskRepository;
    private final InboundItemRepository inboundItemRepository;
    private final InventoryRepository inventoryRepository;
    private final OutboundTaskRepository outboundTaskRepository;
    private final OutboundItemRepository outboundItemRepository;

    @Value("${dataInit}")
    private boolean enabled;

    @Autowired
    public DataSourceInitializer(UserRepository userRepository, WorkerRepository workerRepository,
                                 OrderRepository orderRepository, ProductRepository productRepository,
                                 OrderItemRepository orderItemRepository, InboundTaskRepository inboundTaskRepository,
                                 InboundItemRepository inboundItemRepository, InventoryRepository inventoryRepository,
                                 OutboundTaskRepository outboundTaskRepository, OutboundItemRepository outboundItemRepository) {
        this.userRepository = userRepository;
        this.workerRepository = workerRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
        this.inboundTaskRepository = inboundTaskRepository;
        this.inboundItemRepository = inboundItemRepository;
        this.inventoryRepository = inventoryRepository;
        this.outboundTaskRepository = outboundTaskRepository;
        this.outboundItemRepository = outboundItemRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!enabled) {
            return;
        }

        // 添加用户数据
        List<User> users = new ArrayList<>();
        User user1 = User.builder().name("user").password("61259cdf-9cb1-4981-b926-35ebe0906c29").build();
        User user2 = User.builder().name("Test").password("Test").build();
        User user3 = User.builder().name("zzq").password("123").build();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        userRepository.saveAll(users);

        // 添加工人数据
        List<Worker> workers = new ArrayList<>();
        Worker worker1 = Worker.builder().name("zzq").build();
        workers.add(worker1);
        workerRepository.saveAll(workers);

        // 添加产品数据
        List<Product> products = new ArrayList<>();
        Product product1 = Product.builder().name("iPhone 13").price(new BigDecimal("999.99")).build();
        Product product2 = Product.builder().name("Samsung Galaxy S22").price(new BigDecimal("1099.99")).build();
        products.add(product1);
        products.add(product2);
        productRepository.saveAll(products);

        // 添加订单数据
        List<Order> orders = new ArrayList<>();
        Order order1 = Order.builder().user(users.get(0)).status("Pending").createdAt(LocalDateTime.now()).build();
        orders.add(order1);
        orderRepository.saveAll(orders);

        // 添加订单商品数据
        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem orderItem1 = OrderItem.builder().order(order1).product(product1).quantity(2).build();
        orderItems.add(orderItem1);
        orderItemRepository.saveAll(orderItems);

        // 添加入库任务数据
        List<InboundTask> inboundTasks = new ArrayList<>();
        InboundTask inboundTask1 = InboundTask.builder().status("Pending").createdAt(LocalDateTime.now()).build();
        inboundTasks.add(inboundTask1);
        inboundTaskRepository.saveAll(inboundTasks);

        // 添加入库货物数据
        List<InboundItem> inboundItems = new ArrayList<>();
        InboundItem inboundItem1 = InboundItem.builder().inboundTask(inboundTask1).product(product1).quantity(10).productionDate(LocalDate.now()).build();
        inboundItems.add(inboundItem1);
        inboundItemRepository.saveAll(inboundItems);

        // 添加库存数据
        List<Inventory> inventories = new ArrayList<>();
        Inventory inventory1 = Inventory.builder().product(product1).quantity(10).lastUpdated(LocalDateTime.now()).build();
        inventories.add(inventory1);
        inventoryRepository.saveAll(inventories);

        // 添加出库任务数据
        List<OutboundTask> outboundTasks = new ArrayList<>();
        OutboundTask outboundTask1 = OutboundTask.builder().order(order1).status("Pending").createdAt(LocalDateTime.now()).build();
        outboundTasks.add(outboundTask1);
        outboundTaskRepository.saveAll(outboundTasks);

        // 添加出库货物数据
        List<OutboundItem> outboundItems = new ArrayList<>();
        OutboundItem outboundItem1 = OutboundItem.builder().outboundTask(outboundTask1).inboundItem(inboundItem1).quantity(5).build();
        outboundItems.add(outboundItem1);
        outboundItemRepository.saveAll(outboundItems);
    }
}

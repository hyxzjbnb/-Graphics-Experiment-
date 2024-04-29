package com.example.ex3_2_back.service;

import com.example.ex3_2_back.entity.*;
import com.example.ex3_2_back.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.ex3_2_back.entity.*;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private InventoryRepository inventoryRepository;

    @Transactional
    public Order createOrder(Order order) {
        // Assume that the inventory checks and user verification are already done
        order.setStatus("Pending");
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        return orderRepository.save(order);
    }

    @Transactional
    public Order updateOrderStatus(Integer orderId, String status) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        order.setUpdatedAt(LocalDateTime.now());
        return orderRepository.save(order);
    }

    @Transactional
    public void cancelOrder(Integer orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        if (!order.getStatus().equals("Shipped")) {
            order.setStatus("Cancelled");
            order.setUpdatedAt(LocalDateTime.now());
            orderRepository.save(order);
            // Additional logic to restore inventory
        } else {
            throw new IllegalStateException("Cannot cancel a shipped order.");
        }
    }

    @Transactional(readOnly = true)
    public List<Order> getOrders(Integer userId, String status) {
        if (userId != null && status != null) {
            return orderRepository.findByCustomer_IdAndStatus(userId, status);
        } else if (userId != null) {
            return orderRepository.findByCustomer_Id(userId);
        } else if (status != null) {
            return orderRepository.findByStatus(status);
        } else {
            return orderRepository.findAll();
        }
    }
}

package com.example.ex3_2_back.service;

import com.example.ex3_2_back.entity.OrderException;
import com.example.ex3_2_back.repository.OrderExceptionRepository;
import com.example.ex3_2_back.repository.OrderRepository;
import com.example.ex3_2_back.entity.*;
import com.example.ex3_2_back.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.ex3_2_back.entity.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
/**
 * @author hyxzjbnb
 * @create 2024-04-15-1:17
 */
@Service
@Slf4j
public class OrderExceptionService {

    @Autowired
    private OrderExceptionRepository orderExceptionRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public OrderException recordException(Integer orderId, String description) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        OrderException exception = new OrderException();
        exception.setOrder(order);
        exception.setDescription(description);
        exception.setCreatedAt(LocalDateTime.now());
        return orderExceptionRepository.save(exception);
    }

    @Transactional
    public OrderException updateException(Integer exceptionId, String newDescription) {
        OrderException exception = orderExceptionRepository.findById(exceptionId)
                .orElseThrow(() -> new RuntimeException("OrderException not found"));
        exception.setDescription(newDescription);
        return orderExceptionRepository.save(exception);
    }

    @Transactional
    public void resolveException(Integer exceptionId) {
        OrderException exception = orderExceptionRepository.findById(exceptionId)
                .orElseThrow(() -> new RuntimeException("OrderException not found"));
        // Suppose resolved status is represented by an empty description or specific status
        exception.setDescription("Resolved");
        orderExceptionRepository.save(exception);
    }


    @Transactional
    public List<OrderException> getExceptions(Integer orderId, LocalDate date) {
        if (orderId != null && date != null) {
            return orderExceptionRepository.findByOrder_orderIdAndCreatedAt(orderId, date);
        } else if (orderId != null) {
            return orderExceptionRepository.findByOrder_orderId(orderId);
        } else if (date != null) {
            return orderExceptionRepository.findByCreatedAt(date);
        } else {
            return orderExceptionRepository.findAll();
        }
    }
}


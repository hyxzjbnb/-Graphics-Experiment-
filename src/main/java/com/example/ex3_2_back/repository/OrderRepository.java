package com.example.ex3_2_back.repository;

import com.example.ex3_2_back.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

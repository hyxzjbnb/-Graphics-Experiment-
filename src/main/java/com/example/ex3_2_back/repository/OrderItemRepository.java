package com.example.ex3_2_back.repository;

import com.example.ex3_2_back.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    // 可以添加更多的查询方法
}

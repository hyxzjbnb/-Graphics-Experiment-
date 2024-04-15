package com.example.ex3_2_back.repository;

import com.example.ex3_2_back.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByCustomer_IdAndStatus(Integer userId, String status);
    List<Order> findByCustomer_Id(Integer userId);
    List<Order> findByStatus(String status);
}

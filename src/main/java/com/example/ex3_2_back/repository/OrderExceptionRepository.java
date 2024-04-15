package com.example.ex3_2_back.repository;

import com.example.ex3_2_back.entity.OrderException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.time.LocalDate;

@Repository
public interface OrderExceptionRepository extends JpaRepository<OrderException, Integer> {
    List<OrderException> findByOrder_orderId(Integer orderId);
    List<OrderException> findByCreatedAt(LocalDate createdAt);
    List<OrderException> findByOrder_orderIdAndCreatedAt(Integer orderId, LocalDate createdAt);
}

package com.example.ex3_2_back.repository;

import com.example.ex3_2_back.entity.Product;
import com.example.ex3_2_back.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Operation(summary = "通过产品名查找")
    @RestResource(path = "findByProductId")
    Optional<Product> findByProductId(Integer ProductId);
}

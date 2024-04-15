package com.example.ex3_2_back.repository;

import com.example.ex3_2_back.entity.Inventory;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
    // 可以添加更多的查询方法
    @Operation(summary = "通过产品id查找")
    @RestResource(path = "findByProductId")
    Optional<Inventory> findByProduct_ProductId(Integer productId);

}

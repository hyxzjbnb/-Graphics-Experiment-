package com.example.ex3_2_back.repository;

import com.example.ex3_2_back.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "InventoryRepository")
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    // 更新货物信息
    Inventory save(Inventory inventory);
}

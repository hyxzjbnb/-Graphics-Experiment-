package com.example.ex3_2_back.service;

import com.example.ex3_2_back.repository.InventoryRepository;
import com.example.ex3_2_back.entity.*;
import com.example.ex3_2_back.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.ex3_2_back.entity.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hyxzjbnb
 * @create 2024-04-15-1:18
 */
@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public Inventory updateInventory(Integer productId, int changeInQuantity) {
        Inventory inventory = inventoryRepository.findByProduct_ProductId(productId)
                .orElseThrow(() -> new RuntimeException("Inventory not found for Product ID: " + productId));
        int newQuantity = inventory.getQuantity() + changeInQuantity;
        if (newQuantity < 0) {
            throw new IllegalStateException("Cannot reduce inventory below zero.");
        }
        inventory.setQuantity(newQuantity);
        inventory.setLastUpdated(LocalDateTime.now());
        return inventoryRepository.save(inventory);
    }

    public List<Inventory> checkLowInventory() {
        List<Inventory> lowInventoryItems = inventoryRepository.findAll().stream()
                .filter(inv -> inv.getQuantity() < 10) // Assume 10 as the threshold for low inventory
                .collect(Collectors.toList());
        return lowInventoryItems;
    }

    @Transactional
    public void auditInventory() {
        List<Inventory> inventories = inventoryRepository.findAll();
        for (Inventory inventory : inventories) {
            // Implement audit logic, possibly updating the status or flags
            inventory.setLastUpdated(LocalDateTime.now());
            inventoryRepository.save(inventory);
        }
    }

    public int getInventoryQuantity(Integer productId) {
        Inventory inventory = inventoryRepository.findByProduct_ProductId(productId)
                .orElseThrow(() -> new RuntimeException("Inventory not found for Product ID: " + productId));
        return inventory.getQuantity();
    }
}


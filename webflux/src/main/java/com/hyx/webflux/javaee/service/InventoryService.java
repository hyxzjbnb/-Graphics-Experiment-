package com.hyx.webflux.javaee.service;

import com.hyx.webflux.javaee.model.Inventory;
import com.hyx.webflux.javaee.repository.InventoryRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author hyxzjbnb
 * @create 2024-05-29-16:30
 */
@Service
@Slf4j
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    //更新表
    @Transactional
    public Mono<Inventory> updateInventory(Inventory inventory) {
        int Id = inventory.getPid();
        int changeInQuantity = inventory.getQuantity();
        return  inventoryRepository.findById(Id)
                .flatMap(Inventory -> {
                    Inventory.setQuantity(changeInQuantity);
                    return inventoryRepository.save(Inventory);
                });
    }

    //保存表
    public Mono<Inventory> createInventory(Inventory inventory){
        //log.info("{}",order);
        return inventoryRepository.save(inventory);
    }
    //

    public Flux<Integer> getInventoryQuantity(Integer productId) {
        log.info("{}",productId);
        return inventoryRepository.findByPid(productId)
                .map(inventory -> inventory.getQuantity())
                .switchIfEmpty(Mono.error(new RuntimeException("Product not found")));
    }

    // 删除表
    @Transactional
    public Mono<Void> deleteInventory(Integer productId) {
        return inventoryRepository.findById(productId)
                .flatMap(inventory -> inventoryRepository.delete(inventory));
    }
}

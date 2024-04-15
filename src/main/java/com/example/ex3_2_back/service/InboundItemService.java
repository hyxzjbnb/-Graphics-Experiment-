package com.example.ex3_2_back.service;

import com.example.ex3_2_back.entity.InboundItem;
import com.example.ex3_2_back.entity.InboundTask;
import com.example.ex3_2_back.repository.InboundItemRepository;
import com.example.ex3_2_back.repository.InboundTaskRepository;
import com.example.ex3_2_back.entity.*;
import com.example.ex3_2_back.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.ex3_2_back.entity.*;
import java.time.LocalDate;

/**
 * @author hyxzjbnb
 * @create 2024-04-15-1:18
 */
@Service
public class InboundItemService {

    @Autowired
    private InboundItemRepository inboundItemRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private InboundTaskRepository inboundTaskRepository;

    @Transactional
    public InboundItem addInboundItem(Integer taskId, Integer productId, Integer quantity, LocalDate productionDate) {
        InboundTask task = inboundTaskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("InboundTask not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        InboundItem item = new InboundItem();
        item.setInboundTask(task);
        item.setProduct(product);
        item.setQuantity(quantity);
        item.setProductionDate(productionDate);
        return inboundItemRepository.save(item);
    }

    @Transactional
    public InboundItem updateInboundItem(Integer itemId, Integer newQuantity, LocalDate newProductionDate) {
        InboundItem item = inboundItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("InboundItem not found"));
        item.setQuantity(newQuantity);
        item.setProductionDate(newProductionDate);
        return inboundItemRepository.save(item);
    }

    @Transactional
    public void deleteInboundItem(Integer itemId) {
        InboundItem item = inboundItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("InboundItem not found"));
        inboundItemRepository.delete(item);
    }
}

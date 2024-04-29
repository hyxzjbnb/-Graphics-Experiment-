package com.example.ex3_2_back.service;

import com.example.ex3_2_back.entity.OutboundItem;
import com.example.ex3_2_back.repository.OutboundItemRepository;
import com.example.ex3_2_back.repository.OutboundTaskRepository;
import com.example.ex3_2_back.repository.*;
import com.example.ex3_2_back.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
/**
 * @author hyxzjbnb
 * @create 2024-04-15-1:19
 */
@Service
@Slf4j
public class OutboundItemService {

    @Autowired
    private OutboundItemRepository outboundItemRepository;
    @Autowired
    private InboundItemRepository inboundItemRepository;
    @Autowired
    private OutboundTaskRepository outboundTaskRepository;

    @Transactional
    public OutboundItem addOutboundItem(Integer taskId, Integer productId, Integer quantity) {
        OutboundTask task = outboundTaskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("OutboundTask not found"));
        InboundItem inboundItem = inboundItemRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        OutboundItem item = new OutboundItem();
        item.setOutboundTask(task);
        item.setInboundItem(inboundItem);
        item.setQuantity(quantity);
        return outboundItemRepository.save(item);
    }

    @Transactional
    public OutboundItem updateOutboundItem(Integer itemId, Integer newQuantity) {
        OutboundItem item = outboundItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("OutboundItem not found"));
        item.setQuantity(newQuantity);
        return outboundItemRepository.save(item);
    }

    @Transactional
    public void deleteOutboundItem(Integer itemId) {
        OutboundItem item = outboundItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("OutboundItem not found"));
        outboundItemRepository.delete(item);
    }
}

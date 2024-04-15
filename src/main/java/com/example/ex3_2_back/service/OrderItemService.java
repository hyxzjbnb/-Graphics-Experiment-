package com.example.ex3_2_back.service;
import com.example.ex3_2_back.entity.*;
import com.example.ex3_2_back.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.ex3_2_back.entity.*;
/**
 * @author hyxzjbnb
 * @create 2024-04-15-1:16
 */
@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private InventoryRepository inventoryRepository;

    @Transactional
    public OrderItem addOrderItem(OrderItem orderItem) {
        Product product = productRepository.findByProductId(orderItem.getProduct().getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Inventory inventory = inventoryRepository.findByProduct_ProductId(product.getProductId())
                .orElseThrow(() -> new RuntimeException("Inventory not found"));

        if (inventory.getQuantity() >= orderItem.getQuantity()) {
            inventory.setQuantity(inventory.getQuantity() - orderItem.getQuantity());
            inventoryRepository.save(inventory);
            return orderItemRepository.save(orderItem);
        } else {
            throw new RuntimeException("Insufficient inventory for product ID: " + product.getProductId());
        }
    }

    @Transactional
    public OrderItem updateOrderItem(Integer orderItemId, Integer newQuantity) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new RuntimeException("OrderItem not found"));
        Product product = productRepository.findById(orderItem.getProduct().getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Inventory inventory = inventoryRepository.findByProduct_ProductId(product.getProductId())
                .orElseThrow(() -> new RuntimeException("Inventory not found"));

        int diffQuantity = newQuantity - orderItem.getQuantity();
        if (inventory.getQuantity() >= diffQuantity) {
            inventory.setQuantity(inventory.getQuantity() - diffQuantity);
            inventoryRepository.save(inventory);
            orderItem.setQuantity(newQuantity);
            return orderItemRepository.save(orderItem);
        } else {
            throw new RuntimeException("Insufficient inventory to update the quantity for product ID: " + product.getProductId());
        }
    }

    @Transactional
    public void removeOrderItem(Integer orderItemId) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new RuntimeException("OrderItem not found"));
        Inventory inventory = inventoryRepository.findByProduct_ProductId(orderItem.getProduct().getProductId())
                .orElseThrow(() -> new RuntimeException("Inventory not found"));
        inventory.setQuantity(inventory.getQuantity() + orderItem.getQuantity());
        inventoryRepository.save(inventory);
        orderItemRepository.delete(orderItem);
    }
}

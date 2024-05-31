package com.hyx.webflux.javaee.service;

import com.hyx.webflux.javaee.model.Order;
import com.hyx.webflux.javaee.model.OrderItem;
import com.hyx.webflux.javaee.repository.OrderItemRepository;
import com.hyx.webflux.javaee.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * @author hyxzjbnb
 * @create 2024-05-29-17:21
 */
@Service
@Slf4j
public class OrderItemService {
    @Autowired
    private OrderItemRepository orderitemRepository;

    public Mono<OrderItem> createOrder(OrderItem orderitem) {
        //log.info("{}",order);
        return orderitemRepository.save(orderitem);
    }

    // 更新订单商品数量
    public Mono<OrderItem> updateOrderItem(Integer orderItemId, Integer newQuantity) {
        // 实现更新逻辑
        // 例如，查询订单商品，更新数量，保存更新后的订单商品
        return orderitemRepository.findById(orderItemId)
                .flatMap(orderItem -> {
                    orderItem.setQuantity(newQuantity);
                    return orderitemRepository.save(orderItem);
                });
    }

    // 删除订单商品
    public Mono<Void> removeOrderItem(Integer orderItemId) {
        // 实现删除逻辑
        // 例如，查询订单商品，删除订单商品，更新库存
        return orderitemRepository.findById(orderItemId)
                .flatMap(orderItem -> orderitemRepository.delete(orderItem));
    }

    public Mono<OrderItem> getOrderItem(Integer orderItemId) {
        return orderitemRepository.findById(orderItemId)
                .switchIfEmpty(Mono.error(new RuntimeException("Order item not found")));
    }

}

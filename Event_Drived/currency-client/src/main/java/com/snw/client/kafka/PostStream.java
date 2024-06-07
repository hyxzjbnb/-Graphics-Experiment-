package com.snw.client.kafka;

import com.snw.client.event.GoodsArrivedEvent;
import com.snw.client.event.UnloadingCompletedEvent;
import com.snw.client.event.UnloadingStartedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * @author hyxzjbnb
 * @create 2024-06-07-22:07
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class PostStream {

    private final StreamBridge streamBridge;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    // 发布 GoodsArrived 事件
    public void publishGoodsArrived(String shipmentId, String warehouseId, String itemId, int quantity, String arrivalTime) {
        GoodsArrivedEvent event = new GoodsArrivedEvent(shipmentId, warehouseId, itemId, quantity, arrivalTime);
        messagingTemplate.convertAndSend("/topic/goodsArrived", event);
        streamBridge.send("goods-arrived-out-0", event);
        log.info("GoodsArrived event sent: {}", event);
    }
   //发布开始卸货事件
    public void publishUnloadingStarted(String shipmentId, String warehouseId, String startTime) {
        UnloadingStartedEvent event = new UnloadingStartedEvent(shipmentId, warehouseId, startTime);
        messagingTemplate.convertAndSend("/topic/unloadingStarted", event);
        log.info("Received UnloadingStarted event: " + event);
        streamBridge.send("unloading-started-out-0", event);
        log.info("UnloadingStarted event sent: {}", event);
    }
   //发布解释卸货事件
    public void publishUnloadingCompleted(String shipmentId, String warehouseId, String completionTime,String vehicleId) {
        UnloadingCompletedEvent event = new UnloadingCompletedEvent(shipmentId, warehouseId, completionTime,vehicleId);
        messagingTemplate.convertAndSend("/topic/unloadingCompleted", event);
        log.info("Received UnloadingCompleted event: " + event);
        streamBridge.send("unloading-completed-out-0", event);
        log.info("UnloadingCompleted event sent: {}", event);
    }
}

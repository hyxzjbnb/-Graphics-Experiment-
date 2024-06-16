package com.snw.client.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snw.client.event.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

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
    public void publishUnloadingStarted(String shipmentId, String warehouseId, String startTime,String vehicle) {
        UnloadingStartedEvent event = new UnloadingStartedEvent(shipmentId, warehouseId, startTime,vehicle);
        messagingTemplate.convertAndSend("/topic/unloadingStarted", event);
        log.info("Received UnloadingStarted event: " + event);
        streamBridge.send("unloading-started-out-0", event);
        log.info("UnloadingStarted event sent: {}", event);
    }
   //发布结束卸货事件
    public void publishUnloadingCompleted(String shipmentId, String warehouseId, String completionTime,String vehicleId) {
        UnloadingCompletedEvent event = new UnloadingCompletedEvent(shipmentId, warehouseId, completionTime,vehicleId);
        messagingTemplate.convertAndSend("/topic/unloadingCompleted", event);
        log.info("Received UnloadingCompleted event: " + event);
        streamBridge.send("unloading-completed-out-0", event);
        log.info("UnloadingCompleted event sent: {}", event);
    }


    //开始入库流程的编写
    public void processInspection(String id, String inspectionTime, Map<String, String> documents) {
        // 构建事件
        InspectionEvent event = new InspectionEvent(id, inspectionTime, documents);
        // 发布事件到 Kafka 主题
        streamBridge.send("inspection-documents-topic", event);
        log.info("Inspection event sent: {}", event);
    }

    //开始出库流程的编写
    public void publishOrderReceived(OrderReceivedEvent event){
        streamBridge.send("order-received",event);
        log.info("OrderReceived event sent: {}", event);
    }
    //发布开始出库结束，然后派车前来
    public void PublishGoodsShipped(GoodsShippedEvent event){
        streamBridge.send("deliver-begin",event);
        log.info("出库结束: {}", event);
    }
    //发布出库运输完成
    public void PublishGoodsShippedComplete(GoodsShippedCompleteEvent event){
        streamBridge.send("deliver-finish",event);
        log.info("出库结束: {}", "");
    }


}

package com.snw.api.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.snw.api.event.GoodsArrivedEvent;
import com.snw.api.event.UnloadingCompletedEvent;
import com.snw.api.event.UnloadingStartedEvent;
import com.snw.api.service.InboundService;
import com.snw.api.service.VehicleService;
import com.snw.api.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author hyxzjbnb
 * @create 2024-06-07-22:08
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class GetStream {

    private static final Logger log = LoggerFactory.getLogger(GetStream.class);

    @Autowired
    private PostStreamer streamer;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private InboundService inboundService;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private WarehouseService warehouseService;

    @KafkaListener(topics = {"goods-arrived-out-0", "unloading-started-out-0", "unloading-completed-out-0", "assignment-result-out-0"}, groupId = "group_id")
    public void listen(ConsumerRecord<String, byte[]> record) {
        try {
            String topic = record.topic();
            byte[] message = record.value();

            if (topic.equals("goods-arrived-out-0")) {
                GoodsArrivedEvent event = objectMapper.readValue(message, GoodsArrivedEvent.class);
                handleGoodsArrived(event);
            } else if (topic.equals("unloading-started-out-0")) {
                UnloadingStartedEvent event = objectMapper.readValue(message, UnloadingStartedEvent.class);
                handleUnloadingStarted(event);
            } else if (topic.equals("unloading-completed-out-0")) {
                UnloadingCompletedEvent event = objectMapper.readValue(message, UnloadingCompletedEvent.class);
                handleUnloadingCompleted(event);
            } else {
                log.info("Unknown event type received: " + new String(message));
            }
        } catch (Exception e) {
            log.error("Failed to process message", e);
        }
    }
    //处理卸货开始
    private void handleUnloadingStarted(UnloadingStartedEvent event) {
        //将入库json改成卸货中
        inboundService.startUnloading(event.getShipmentId());
        streamer.publishResultEvent("","handleUnloadingStarted");
    }
    //处理卸货结束
    private void handleUnloadingCompleted(UnloadingCompletedEvent event) {
        //将入库json改成已完成卸货，将对应车子改成空闲中，同时发布检查事件
        inboundService.completeUnloading(event.getShipmentId());
        vehicleService.updateVehicleStatus("V001","idle");
    }
    //收到到达消息后事件的操作
    public void handleGoodsArrived(GoodsArrivedEvent event) {

        // 保存事件到inbound.json文件
        inboundService.saveGoodsArrivedEvent(event);

        // 检查是否有可用的车辆
        ObjectNode allocatedVehicle = vehicleService.findAndAllocateVehicle();
        if (allocatedVehicle == null) {
            log.info("No available vehicle to handle the shipment");
            //失败
            streamer.publishAssignmentResultEvent(event, "null","failure",0);
            return;
        }

        // 查找可以容纳货物的仓库
        int totalQuantity = event.getQuantity();
        ObjectNode allocatedWarehouse = warehouseService.findAndAllocateWarehouse(totalQuantity);
        if (allocatedWarehouse == null) {
            log.info("No available warehouse with sufficient space for the shipment");

            // 恢复车辆状态
            vehicleService.updateVehicleStatus(allocatedVehicle.get("id").asText(), "idle");
            //这里之后可以加一个重复调度？？
            //失败
            streamer.publishAssignmentResultEvent(event, "null","failure",0);
            return;
        }

        // 计算到达时间
        int distance = allocatedWarehouse.get("distanceFromLogisticPoint").asInt();
        int speed = allocatedVehicle.get("speed").asInt();
        int travelTime = distance / speed;  // 简单的距离/速度计算
        log.info("Assigned vehicle {} to warehouse {}. Estimated travel time: {} units",
                allocatedVehicle.get("id").asText(),
                allocatedWarehouse.get("id").asText(),
                travelTime);
        // 发布分配成功事件
        streamer.publishAssignmentResultEvent(event, allocatedVehicle.get("id").asText(),"success",travelTime);

    }

}

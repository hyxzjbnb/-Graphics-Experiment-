package com.snw.api.kafka;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.snw.api.event.*;
import com.snw.api.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

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

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private OutboundService outboundService;
    @Autowired
    private liftService liftService;

    @KafkaListener(topics = {"goods-arrived-out-0", "unloading-started-out-0", "unloading-completed-out-0", "inspection-documents-topic", "order-received","deliver-begin","deliver-finish"}, groupId = "warehouse")
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
            } else if (topic.equals("inspection-documents-topic")) {
                InspectionEvent event = objectMapper.readValue(message, InspectionEvent.class);
                handleInspection(event);
            } else if (topic.equals("order-received")) {
                OrderReceivedEvent event = objectMapper.readValue(message, OrderReceivedEvent.class);
                handleOrderReceived(event);
            } else if (topic.equals("deliver-begin")) {
                GoodsShippedEvent event = objectMapper.readValue(message, GoodsShippedEvent.class);
                handleGoodsShipped(event);
            }else if (topic.equals("deliver-finish")) {
                GoodsShippedCompleteEvent event = objectMapper.readValue(message, GoodsShippedCompleteEvent.class);
                handleGoodsShippedComplete(event);
            }else {
                log.info("Unknown event type received: " + new String(message));
            }
        } catch (Exception e) {
            log.error("Failed to process message", e);
        }
    }

    // 处理 OrderReceived 事件
    private void handleOrderReceived(OrderReceivedEvent event) {
        log.info("Handling OrderReceivedEvent: {}", event);
        String orderId = event.getOrderId();
        String itemId = event.getItemId();
        int quantity = event.getQuantity();

        ObjectNode a = inventoryService.isStockAvailable(itemId, quantity);

        // 检查库存
        if (a!=null) {
            ObjectNode b = liftService.findAndAllocateLift(a.get("location").asText());
            if(b!=null){
                try {// 减少库存
                    inventoryService.decreaseStock(itemId, quantity);
                    // 生成出库信息
                    JsonNode location = a.get("warehouseLocation");
                    log.info(a.get("shipmentId").asText());
                    int Speed = b.get("speed").asInt();

                    ObjectMapper objectMapper = new ObjectMapper();

                    // 从 JsonNode 中提取 x, y, z 的值
                    int xValue = location.get("x").asInt();
                    int yValue = location.get("y").asInt();
                    int zValue = location.get("z").asInt();

                    //计算时间
                    // 计算勾股边并加上 z
                    double hypotenuse = Math.sqrt(Math.pow(xValue, 2) + Math.pow(yValue, 2));
                    double result = hypotenuse + zValue;

                    // 将结果取整并除以 speed
                    int finalResult = (int) result / Speed;
                    log.info(String.valueOf(finalResult));
                    log.info(b.get("id").asText());
                    outboundService.createOutboundRecord(orderId, itemId, quantity);
                    streamer.publishOrderResultEvent("success",finalResult,b.get("id").asText());
                    log.info("Order processed successfully: {}", orderId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                streamer.publishOrderResultEvent("failure",0,null);
                log.warn("Not enough lift for order: {}", orderId);
            }
        } else {
            streamer.publishOrderResultEvent("failure",0,null);
            log.warn("Not enough stock for order: {}", orderId);
        }
    }
    //处理出库运输事件
    public void handleGoodsShipped(GoodsShippedEvent event) {
        String liftId = event.getLiftId();

        //升降机恢复空闲
        liftService.updateLiftStatus(liftId,"idle");
        // 1. 查找升降机的location
        ObjectNode lift = liftService.getLiftById(liftId);
        if (lift == null) {
            streamer.publishOrderResultEvent("failure",0,null);
            System.out.println("No lift found with ID: " + liftId);
            return;
        }
        String location = lift.get("location").asText();

        // 2. 查找指定location的空闲车辆
        ObjectNode vehicle = findIdleVehicleAtLocation(location);
        if (vehicle == null) {
            streamer.publishOrderResultEvent("failure",0,null);
            System.out.println("No idle vehicle found at location: " + location);
            return;
        }

        // 3. 更新车辆状态为in_use
        String vehicleId = vehicle.get("id").asText();
        vehicleService.updateVehicleStatus(vehicleId, "in_use");

        // 4. 查找location到目标点的距离
        int distance = warehouseService.getWarehouseById(location).get("distanceFromLogisticPoint").asInt();

        // 5. 根据车辆的速度计算时间
        double speed = vehicle.get("speed").asDouble();
        int transportTime = calculateTransportTime(distance, speed);

        // 6. 返回完成时间和车辆
        saveTransportTime(event.getOrderId(), transportTime);
        streamer.publishOrderResultEvent("success",transportTime,vehicle.get("id").asText());
    }

    //处理出库完成
    public void handleGoodsShippedComplete(GoodsShippedCompleteEvent event){
        // 1. 更新车辆状态为idle
        String vehicleId = event.getVehicleId();
        vehicleService.updateVehicleStatus(vehicleId, "idle");
        //2.更新outbound状态为down
        outboundService.updateOutboundStatusToDown(event.getOrderId());
        streamer.publishOrderResultEvent("success",0,event.getVehicleId());
    }

    // 查找指定location的空闲车辆
    private ObjectNode findIdleVehicleAtLocation(String location) {
        ArrayNode allVehicles = vehicleService.getAllVehicles();
        for (JsonNode node : allVehicles) {
            if (node instanceof ObjectNode) {
                ObjectNode vehicle = (ObjectNode) node;
                if ("idle".equals(vehicle.get("status").asText()) && location.equals(vehicle.get("location").asText())) {
                    return vehicle;
                }
            }
        }
        return null;
    }

    // 根据距离和速度计算运输时间
    private int calculateTransportTime(double distance, double speed) {
        if (speed <= 0) {
            throw new IllegalArgumentException("Speed must be greater than zero");
        }
        int time = (int)(distance / speed);
        return time; // 时间 = 距离 / 速度
    }

    // 保存运输时间（假设实现）
    private void saveTransportTime(String orderId, double transportTime) {
        // 这里假设有一个方法来保存运输时间
        System.out.println("Order " + orderId + ": Transport time is " + transportTime + " hours.");
    }

    //处理卸货开始
    private void handleUnloadingStarted(UnloadingStartedEvent event) {
        log.info("接收到卸货信号");
        //将入库json改成卸货中
        inboundService.startUnloading(event.getShipmentId());
        streamer.publishResultEvent("success", "handleUnloadingStarted");
    }

    //处理卸货结束
    private void handleUnloadingCompleted(UnloadingCompletedEvent event) {
        log.info("接收到结束卸货信号");
        //将入库json改成已完成卸货，将对应车子改成空闲中，同时等待检查事件
        inboundService.completeUnloading(event.getShipmentId());
        vehicleService.updateVehicleStatus(event.getVehicleId(), "idle");
        streamer.publishResultEvent("success", "handleUnloadingCompleted");
    }

    //收到到达消息后事件的操作
    public void handleGoodsArrived(GoodsArrivedEvent event) {
        // 保存事件到inbound.json文件
        inboundService.saveGoodsArrivedEvent(event);

        // 检查是否有可用的车辆
        ObjectNode allocatedVehicle = vehicleService.findAndAllocateVehicle();
        if (allocatedVehicle == null) {
            log.info("No available vehicle to handle the shipment");
            streamer.publishAssignmentResultEvent(event, "null", "failure", 0);
            return;
        }

        // 查找可以容纳货物的仓库
        int totalQuantity = event.getQuantity();
        ObjectNode allocatedWarehouse = warehouseService.findAndAllocateWarehouse(totalQuantity);
        if (allocatedWarehouse == null) {
            log.info("No available warehouse with sufficient space for the shipment");

            // 恢复车辆状态
            vehicleService.updateVehicleStatus(allocatedVehicle.get("id").asText(), "idle");
            streamer.publishAssignmentResultEvent(event, "null", "failure", 0);
            return;
        }
        event.setWarehouseId(allocatedWarehouse.get("id").asText());

        // 计算到达时间
        int distance = allocatedWarehouse.get("distanceFromLogisticPoint").asInt();
        int speed = allocatedVehicle.get("speed").asInt();
        int travelTime = distance / speed;  // 简单的距离/速度计算
        log.info("Assigned vehicle {} to warehouse {}. Estimated travel time: {} units",
                allocatedVehicle.get("id").asText(),
                allocatedWarehouse.get("id").asText(),
                travelTime);

        // 发布分配成功事件
        streamer.publishAssignmentResultEvent(event, allocatedVehicle.get("id").asText(), "success", travelTime);
    }

    //入库流程：检查档案
    public void handleInspection(InspectionEvent event) {
        // 更新inbound里指定文件的文档信息，并分配格子
        log.info("Handling InspectionEvent: {}", event);
        // 在这里实现你的文档检查和更新逻辑
    }

}

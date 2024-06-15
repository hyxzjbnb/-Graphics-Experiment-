package com.snw.api.kafka;

import com.snw.api.event.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class PostStreamer {

    private final StreamBridge streamBridge;

    public void publishAssignmentResultEvent(GoodsArrivedEvent event, String vid, String status, int time) {
        // 发布分配失败事件
        if(status == "failure"){
            AssignmentResultEvent failureEvent = new AssignmentResultEvent(
                    event.getShipmentId(), event.getWarehouseId(),  null, "failure", 0
            );
            streamBridge.send("assignment-result-out-0", failureEvent);
            log.info("Assignment result event sent: {}", failureEvent);
        }else{
            // 发布分配成功事件
            AssignmentResultEvent successEvent = new AssignmentResultEvent(
                    event.getShipmentId(), event.getWarehouseId(),  vid, status, time
            );
            streamBridge.send("assignment-result-out-0", successEvent);
            log.info("Assignment result event sent: {}", successEvent);
        }

    }
    //发布检查情况事件
    public void publishResultEvent(String status,String Event) {
        PostResult event = new PostResult(status, Event);
        // 发布分配失败事件
        if(status == "failure"){
            streamBridge.send("result-out-0",event);
            log.info("result event sent: {}", event);
        }else{

            streamBridge.send("result-out-0", event);
            log.info("result event sent: {}", event);
        }

    }

    //发布检查情况事件
    public void publishOrderResultEvent(String status,int time,String liftId) {
        OrderPostResult event = new OrderPostResult(status, time,liftId);
        // 发布分配失败事件
        if(status == "failure"){
            streamBridge.send("OrderResult-out-0",event);
            log.info("result event sent: {}", event);
        }else{

            streamBridge.send("OrderResult-out-0", event);
            log.info("result event sent: {}", event);
        }

    }
    // 发布InspectionCompleted事件
    public void publishInspectionCompletedEvent(InspectionCompletedEvent event) {
        streamBridge.send("inspection-completed-out-0", event);
        log.info("Inspection completed event sent: {}", event);
    }

    // 新增发布 StorageLocationAllocatedEvent 事件的方法
    public void publishStorageLocationAllocatedEvent(StorageLocationAllocatedEvent event) {
        streamBridge.send("storage-location-allocated-out-0", event);
        log.info("Storage location allocated event sent: {}", event);
    }

    public void publishStorageStartedEvent(StorageStartedEvent event) {
        streamBridge.send("storage-started-out-0", event);
        log.info("Storage started event sent: {}", event);
    }
    public void publishStorageCompletedEvent(StorageCompletedEvent event) {
        streamBridge.send("storage-completed-out-0", event);
        log.info("Storage completed event sent: {}", event);
    }
}

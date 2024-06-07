package com.snw.api.kafka;

import com.snw.api.event.AssignmentResultEvent;
import com.snw.api.event.GoodsArrivedEvent;
import com.snw.api.event.PostResult;
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

}

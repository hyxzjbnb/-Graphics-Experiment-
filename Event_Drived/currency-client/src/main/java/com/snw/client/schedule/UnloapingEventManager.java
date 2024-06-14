package com.snw.client.schedule;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snw.client.event.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class UnloapingEventManager {

    // 自动化调度的实现
    private final StreamBridge streamBridge;

    private final ConcurrentMap<String, CompletableFuture<NextEventInfo>> pendingEvents = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private long TypedelayMillis; // 存储延迟时间

    // 发布事件的方法
    public void publishEvent(Object event) {
        String topic = determineTopic(event); // 根据事件类型确定要发布的主题

        if (event instanceof GoodsArrivedEvent) {
            // 处理商品到达事件
            messagingTemplate.convertAndSend("/topic/text", "开始卸货的自动化调度");
            messagingTemplate.convertAndSend("/topic/text", "发起到达申请卸货");
            messagingTemplate.convertAndSend("/topic/goodsArrived", event);
            log.info("GoodsArrived event sent: {}", event);

            // 等待分配结果并处理
            waitForNextEventInfo("assignment-result-out-0", event, topic).thenAccept(nextEventInfo -> {
                if (nextEventInfo.isSuccess()) {
                    TypedelayMillis = nextEventInfo.getTime() * 6 * 100; // 计算延迟时间（单位为毫秒）
                    messagingTemplate.convertAndSend("/topic/text", "需要等待" + TypedelayMillis + "毫秒车才来");
                    log.info("Scheduling UnloadingStartedEvent with delay: {} milliseconds", TypedelayMillis);

                    // 调度卸货开始事件
                    scheduleNextEvent(
                            new UnloadingStartedEvent(
                                    ((GoodsArrivedEvent) event).getShipmentId(),
                                    ((GoodsArrivedEvent) event).getWarehouseId(),
                                    nextEventInfo.getTimestamp(),
                                    nextEventInfo.getVehicleId()
                            ),
                            TypedelayMillis
                    );
                }
            });

        } else if (event instanceof UnloadingStartedEvent) {
            // 处理卸货开始事件
            messagingTemplate.convertAndSend("/topic/unloadingStarted", event);
            log.info("UnloadingStarted event sent: {}", event);

            // 等待结果并处理
            waitForNextEventInfo("result-out-0", event, topic).thenAccept(nextEventInfo -> {
                if (nextEventInfo.isSuccess()) {
                    long delayMillis = TypedelayMillis + nextEventInfo.getTime() * 6 * 100; // 计算总的延迟时间（单位为毫秒）
                    messagingTemplate.convertAndSend("/topic/text", "需要等待" + delayMillis + "毫秒结束卸货");
                    log.info("Scheduling UnloadingCompletedEvent with delay: {} milliseconds", delayMillis);

                    // 调度卸货完成事件
                    scheduleNextEvent(
                            new UnloadingCompletedEvent(
                                    ((UnloadingStartedEvent) event).getShipmentId(),
                                    ((UnloadingStartedEvent) event).getWarehouseId(),
                                    nextEventInfo.getTimestamp(),
                                    ((UnloadingStartedEvent) event).getVehicleId()
                            ),
                            delayMillis
                    );
                }
            });

        } else if (event instanceof UnloadingCompletedEvent) {
            // 处理卸货完成事件
            messagingTemplate.convertAndSend("/topic/unloadingCompleted", event);
            streamBridge.send(topic, event);
            log.info("UnloadingCompleted event sent: {}", event);
            messagingTemplate.convertAndSend("/topic/text", "卸货完成");
            // 对于卸货完成事件，不需要等待 success 回复，可以直接结束流程

        } else {
            // 处理其他类型的事件
            log.info("Unknown event type received.");
        }
    }

    // 调度下一个事件的方法
    private void scheduleNextEvent(Object nextEvent, long delayMillis) {
        log.info("等待调度下一个事件");
        scheduler.schedule(() -> triggerNextEvent(nextEvent), delayMillis, TimeUnit.MILLISECONDS);
    }

    // 等待下一个事件信息的方法
    private CompletableFuture<NextEventInfo> waitForNextEventInfo(String topic, Object event, String a) {
        log.info("等待下一个事件的信息, 主题: {}", topic);
        CompletableFuture<NextEventInfo> result = new CompletableFuture<>();
        pendingEvents.put(topic, result);
        log.info("存储的未来事件信息, 主题: {}", topic);
        streamBridge.send(a, event);
        return result;
    }

    // 触发下一个事件的方法
    private void triggerNextEvent(Object nextEvent) {
        log.info("开始下一个事件的触发");
        publishEvent(nextEvent);
    }

    // 确定事件类型对应的主题的方法
    private String determineTopic(Object event) {
        if (event instanceof GoodsArrivedEvent) {
            return "goods-arrived-out-0";
        } else if (event instanceof UnloadingStartedEvent) {
            return "unloading-started-out-0";
        } else if (event instanceof UnloadingCompletedEvent) {
            return "unloading-completed-out-0";
        } else {
            return "unknown-topic";
        }
    }

    // 处理 PostResult 类型事件的方法
    public void handleResult(PostResult event) {
        log.info("自动化调度获得 handleResult event: {}", event);
        processSuccessResponse("result-out-0", event.getStatus().equals("success"));
    }

    // 处理 AssignmentResultEvent 类型事件的方法
    public void handleAssignmentResult(AssignmentResultEvent event) {
        log.info("自动化调度获得 AssignmentResult event: {}", event);
        processSuccessResponse2("assignment-result-out-0", event.getStatus().equals("success"), event.getTravelTime(), event.getVehicleId());
    }

    // 处理成功响应的方法
    private void processSuccessResponse(String topic, boolean success) {
        log.info("处理成功响应, 主题: {}", topic);
        CompletableFuture<NextEventInfo> future = pendingEvents.remove(topic);
        if (future != null) {
            log.info("完成未来事件信息, 主题: {}", topic);
            future.complete(new NextEventInfo(success));
        } else {
            log.warn("没有找到未来事件的信息, 主题: {}", topic);
        }
    }

    // 处理成功响应的方法（带时间和车辆ID）
    private void processSuccessResponse2(String topic, boolean success, int time, String vehicleId) {
        log.info("处理成功响应, 主题: {}", topic);
        CompletableFuture<NextEventInfo> future = pendingEvents.remove(topic);
        if (future != null) {
            log.info("完成未来事件信息, 主题: {}", topic);
            log.info("是否成功: {}", success);
            future.complete(new NextEventInfo(success, time, vehicleId));
        } else {
            log.warn("没有找到未来事件的信息, 主题: {}", topic);
        }
    }

    // 表示下一事件信息的内部类
    private static class NextEventInfo {
        private final boolean success; // 表示事件是否成功
        private final String timestamp; // 事件的时间戳
        private final int time; // 事件的时间
        private final String vehicleId; // 车辆ID

        public NextEventInfo(boolean success) {
            this(success, System.currentTimeMillis(), null, 5);
        }

        public NextEventInfo(boolean success, int time, String vehicleId) {
            this(success, System.currentTimeMillis(), vehicleId, time);
        }

        public NextEventInfo(boolean success, long timestamp, String vehicleId, int time) {
            this.success = success;
            this.timestamp = String.valueOf(timestamp);
            this.vehicleId = vehicleId;
            this.time = time;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public int getTime() {
            return time;
        }

        public String getVehicleId() {
            return vehicleId;
        }
    }
}

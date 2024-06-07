package com.snw.client.controller;

/**
 * @author hyxzjbnb
 * @create 2024-06-07-17:38
 */

import com.snw.client.domain.GoodsArrivedRequest;
import com.snw.client.domain.Result;
import com.snw.client.domain.UnloadingCompletedRequest;
import com.snw.client.domain.UnloadingStartedRequest;
import com.snw.client.kafka.GetStream;
import com.snw.client.kafka.PostStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/unloading")
public class UnloadingController {

    @Autowired
    private PostStream Streamer;

    // 发布 GoodsArrived 事件的现有方法
    @PostMapping("/goodsArrived")
    public Result goodsArrived(@RequestBody GoodsArrivedRequest request) {
        try {
            Streamer.publishGoodsArrived(
                    request.getShipmentId(),
                    request.getWarehouseId(),
                    request.getItemId(),
                    request.getQuantity(),
                    request.getArrivalTime()
            );
            return Result.success("GoodsArrived event published successfully");
        } catch (Exception e) {
            return Result.error("Failed to publish GoodsArrived event").addDevMessages(e.getMessage());
        }
    }

    // 用于发布 UnloadingStarted 事件
    @PostMapping("/unloadingStarted")
    public Result unloadingStarted(@RequestBody UnloadingStartedRequest request) {
        try {
            Streamer.publishUnloadingStarted(
                    request.getShipmentId(),
                    request.getWarehouseId(),
                    request.getStartTime()
            );
            return Result.success("UnloadingStarted event published successfully");
        } catch (Exception e) {
            return Result.error("Failed to publish UnloadingStarted event").addDevMessages(e.getMessage());
        }
    }

    // 用于发布 UnloadingCompleted 事件
    @PostMapping("/unloadingCompleted")
    public Result unloadingCompleted(@RequestBody UnloadingCompletedRequest request) {
        try {
            Streamer.publishUnloadingCompleted(
                    request.getShipmentId(),
                    request.getWarehouseId(),
                    request.getVehicleId(),
                    request.getCompletionTime()
            );
            return Result.success("UnloadingCompleted event published successfully");
        } catch (Exception e) {
            return Result.error("Failed to publish UnloadingCompleted event").addDevMessages(e.getMessage());
        }
    }
}

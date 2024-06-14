package com.snw.client.controller;

import com.snw.client.domain.GoodsArrivedRequest;
import com.snw.client.domain.Result;
import com.snw.client.event.GoodsArrivedEvent;
import com.snw.client.event.OrderReceivedEvent;
import com.snw.client.kafka.PostStream;
import com.snw.client.schedule.OutboundEventManager;
import com.snw.client.schedule.UnloapingEventManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hyxzjbnb
 * @create 2024-06-14-19:16
 */
@RestController
@RequestMapping("/outbound")
public class OutboundController {
    @Autowired
    private OutboundEventManager eventProcessor;
    @Autowired
    private PostStream Streamer;
    // 发布 orderReceive 事件的现有方法
    @PostMapping("/orderReceive")
    public Result OrderReceived(@RequestBody OrderReceivedEvent request) {
        try {
            Streamer.publishOrderReceived(request);
            return Result.success("GoodsArrived event published successfully");
        } catch (Exception e) {
            return Result.error("Failed to publish GoodsArrived event").addDevMessages(e.getMessage());
        }
    }

    //用于实现事件链
    @PostMapping("/order_receive")
    public String triggerGoodsArrivedEvent(@RequestBody OrderReceivedEvent event) {
        eventProcessor.publishEvent(event);
        return "orderReceive triggered!";
    }
}

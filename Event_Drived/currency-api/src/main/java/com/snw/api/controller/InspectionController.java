package com.snw.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.snw.api.event.InspectionCompletedEvent;
import com.snw.api.kafka.PostStreamer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inspection")
public class InspectionController {

    private final PostStreamer postStreamer;
    private final ObjectMapper objectMapper;

    @Autowired
    public InspectionController(PostStreamer postStreamer, ObjectMapper objectMapper) {
        this.postStreamer = postStreamer;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/complete")
    public ObjectNode completeInspection(@RequestBody ObjectNode inspectionData) {
        // 将请求数据转换为 InspectionCompletedEvent
        InspectionCompletedEvent event = objectMapper.convertValue(inspectionData, InspectionCompletedEvent.class);

        // 发布事件
        postStreamer.publishInspectionCompletedEvent(event);

        // 创建返回的 JSON 数据
        ObjectNode response = objectMapper.createObjectNode();
        response.put("id", inspectionData.get("id").asText());
        response.put("inspectionTime", inspectionData.get("inspectionTime").asText());
        response.set("documents", inspectionData.get("documents"));

        return response;
    }
}

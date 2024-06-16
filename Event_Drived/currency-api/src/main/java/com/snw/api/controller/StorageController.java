package com.snw.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.snw.api.event.StorageCompletedEvent;
import com.snw.api.event.StorageLocationAllocatedEvent;
import com.snw.api.event.StorageStartedEvent;
import com.snw.api.kafka.PostStreamer;
import com.snw.api.service.StorageService;
import com.snw.api.service.liftService; // 确保导入正确的包

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/storage")
public class StorageController {

    private final PostStreamer postStreamer;
    private final ObjectMapper objectMapper;
    private final StorageService storageService;
    private final liftService liftService; // 确保使用正确的实例名称

    @Autowired
    public StorageController(PostStreamer postStreamer, ObjectMapper objectMapper, StorageService storageService, liftService liftService) {
        this.postStreamer = postStreamer;
        this.objectMapper = objectMapper;
        this.storageService = storageService;
        this.liftService = liftService;
    }

    @PostMapping("/allocate")
    public ObjectNode allocateStorage(@RequestBody ObjectNode allocationData) {
        String id = allocationData.get("id").asText();
        int capacity = 100;
        Map<String, Integer> location = new HashMap<>();
        location.put("x", 5);
        location.put("y", 5);
        location.put("z", 2);

        StorageLocationAllocatedEvent event = new StorageLocationAllocatedEvent("success", id, capacity, location);
        storageService.allocateStorageLocation(event);

        ObjectNode response = objectMapper.createObjectNode();
        response.put("status", "success");

        return response;
    }

    @PostMapping("/start")
    public ObjectNode startStorage(@RequestBody ObjectNode storageData) {
        String id = storageData.get("id").asText();
        String startTime = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        StorageStartedEvent event = new StorageStartedEvent(id, startTime);
        String result = liftService.handleStorageStarted(event);

        ObjectNode response = objectMapper.createObjectNode();
        if ("success".equals(result)) {
            response.put("id", id);
            response.put("startTime", startTime);
            response.put("status", "Storage started successfully");
        } else {
            response.put("id", id);
            response.put("status", "Failed to start storage - no available lift");
        }

        return response;
    }

    @PostMapping("/complete")
    public ObjectNode completeStorage(@RequestBody ObjectNode completionData) {
        String shipmentId = completionData.get("id").asText();
        String warehouseId = "WH001";
        String completionTime = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        Map<String, Integer> storageLocation = new HashMap<>();
        storageLocation.put("x", 2);
        storageLocation.put("y", 3);
        storageLocation.put("z", 1);

        StorageCompletedEvent event = new StorageCompletedEvent(shipmentId, warehouseId, completionTime, storageLocation);
        postStreamer.publishStorageCompletedEvent(event);

        ObjectNode response = objectMapper.createObjectNode();
        response.put("shipmentId", shipmentId);
        response.put("warehouseId", warehouseId);
        response.put("completionTime", completionTime);
        response.set("storageLocation", objectMapper.valueToTree(storageLocation));

        return response;
    }
}

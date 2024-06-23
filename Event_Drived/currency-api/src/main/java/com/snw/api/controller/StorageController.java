package com.snw.api.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.snw.api.service.liftService;
import com.snw.api.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/storage")
public class StorageController {

    private final liftService liftService;
    private final WarehouseService warehouseService;

    @Autowired
    public StorageController(liftService liftService, WarehouseService warehouseService) {
        this.liftService = liftService;
        this.warehouseService = warehouseService;
    }

    @PostMapping("/start")
    public ResponseEntity<String> startStorage(@RequestBody ObjectNode request) {
        String warehouseId = request.get("warehouseId").asText();
        String itemId = request.get("itemId").asText();
        int totalQuantity = request.get("totalQuantity").asInt();

        StringBuilder logDetails = new StringBuilder();

        // 分配仓库位置
        ObjectNode storageInfo = warehouseService.findAndAllocateStorage(warehouseId, itemId);
        if (storageInfo == null) {
            logDetails.append("Failed to allocate storage position in warehouse ").append(warehouseId).append("\n");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(logDetails.toString());
        }
        String position = storageInfo.get("position").asText();
        logDetails.append("Allocated storage position ").append(position).append(" in warehouse ").append(warehouseId).append("\n");

        // 分配升降机
        ObjectNode lift = liftService.findAndAllocateLift(position);
        if (lift == null) {
            logDetails.append("Failed to allocate lift for position ").append(position).append("\n");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(logDetails.toString());
        }
        logDetails.append("Allocated lift with ID ").append(lift.get("id").asText()).append(" to position ").append(position).append("\n");

        // 模拟升降机移动
        liftService.simulateLiftMovement(lift, position);
        logDetails.append("Started simulating lift movement to position ").append(position).append("\n");

        return ResponseEntity.ok(logDetails.toString());
    }
}

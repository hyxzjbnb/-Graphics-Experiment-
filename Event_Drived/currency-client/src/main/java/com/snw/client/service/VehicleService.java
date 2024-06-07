package com.snw.client.service;

/**
 * @author hyxzjbnb
 * @create 2024-06-07-20:44
 */
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleService {

    private static final Logger log = LoggerFactory.getLogger(VehicleService.class);
    private static final String VEHICLES_FILE_PATH = "currency-client/src/main/java/com/snw/client/json/vehicles.json";  // vehicles JSON文件路径

    private final ObjectMapper objectMapper;

    public VehicleService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    // 检查和分配空闲的车辆
    public ObjectNode findAndAllocateVehicle() {
        try {
            File file = new File(VEHICLES_FILE_PATH);
            if (!file.exists()) {
                log.warn("Vehicles JSON file does not exist");
                return null;
            }

            ObjectNode jsonData = (ObjectNode) objectMapper.readTree(file);
            ArrayNode vehiclesArray = (ArrayNode) jsonData.get("vehicles");
            List<ObjectNode> availableVehicles = new ArrayList<>();

            // 遍历ArrayNode来获取所有车辆
            for (JsonNode node : vehiclesArray) {
                if (node instanceof ObjectNode) {
                    ObjectNode vehicle = (ObjectNode) node;
                    if ("idle".equals(vehicle.get("status").asText())) {
                        availableVehicles.add(vehicle);
                    }
                }
            }

            if (!availableVehicles.isEmpty()) {
                ObjectNode selectedVehicle = availableVehicles.get(0);  // 选择第一个空闲的车辆
                selectedVehicle.put("status", "in_use");  // 更新车辆状态为“使用中”
                objectMapper.writeValue(file, jsonData);  // 保存更新后的数据
                return selectedVehicle;
            }

        } catch (IOException e) {
            log.error("Error finding and allocating vehicle", e);
        }
        return null;
    }

    // 更新车辆状态
    public void updateVehicleStatus(String vehicleId, String status) {
        try {
            File file = new File(VEHICLES_FILE_PATH);
            if (!file.exists()) {
                log.warn("Vehicles JSON file does not exist");
                return;
            }

            ObjectNode jsonData = (ObjectNode) objectMapper.readTree(file);
            ArrayNode vehiclesArray = (ArrayNode) jsonData.get("vehicles");

            // 遍历ArrayNode来更新车辆状态
            for (JsonNode node : vehiclesArray) {
                if (node instanceof ObjectNode) {
                    ObjectNode vehicle = (ObjectNode) node;
                    if (vehicleId.equals(vehicle.get("id").asText())) {
                        vehicle.put("status", status);
                        objectMapper.writeValue(file, jsonData);  // 保存更新后的数据
                        log.info("Updated status of vehicle {} to {}", vehicleId, status);
                        return;
                    }
                }
            }
        } catch (IOException e) {
            log.error("Error updating vehicle status", e);
        }
    }
}

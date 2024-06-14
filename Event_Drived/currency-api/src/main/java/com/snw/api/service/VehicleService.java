package com.snw.api.service;

/**
 * @author hyxzjbnb
 * @create 2024-06-07-20:18
 */

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleService {

    private static final Logger log = LoggerFactory.getLogger(VehicleService.class);
    private static final String VEHICLES_FILE_PATH = "currency-api/src/main/java/com/snw/api/json/vehicles.json";  // vehicles JSON文件路径
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
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

    // 增加新车辆
    public void addVehicle(ObjectNode newVehicle) {
        try {
            File file = new File(VEHICLES_FILE_PATH);
            if (!file.exists()) {
                log.warn("Vehicles JSON file does not exist");
                return;
            }

            ObjectNode jsonData = (ObjectNode) objectMapper.readTree(file);
            ArrayNode vehiclesArray = (ArrayNode) jsonData.get("vehicles");

            vehiclesArray.add(newVehicle);
            objectMapper.writeValue(file, jsonData);  // 保存更新后的数据
            log.info("Added new vehicle: {}", newVehicle);
        } catch (IOException e) {
            log.error("Error adding vehicle", e);
        }
    }

    // 删除车辆
    public void deleteVehicle(String vehicleId) {
        try {
            File file = new File(VEHICLES_FILE_PATH);
            if (!file.exists()) {
                log.warn("Vehicles JSON file does not exist");
                return;
            }

            ObjectNode jsonData = (ObjectNode) objectMapper.readTree(file);
            ArrayNode vehiclesArray = (ArrayNode) jsonData.get("vehicles");

            for (int i = 0; i < vehiclesArray.size(); i++) {
                JsonNode node = vehiclesArray.get(i);
                if (node instanceof ObjectNode) {
                    ObjectNode vehicle = (ObjectNode) node;
                    if (vehicleId.equals(vehicle.get("id").asText())) {
                        vehiclesArray.remove(i);
                        objectMapper.writeValue(file, jsonData);  // 保存更新后的数据
                        log.info("Deleted vehicle with ID: {}", vehicleId);
                        return;
                    }
                }
            }
            log.warn("Vehicle with ID: {} not found", vehicleId);
        } catch (IOException e) {
            log.error("Error deleting vehicle", e);
        }
    }


    // 更新车辆信息
    public void updateVehicle(String vehicleId, ObjectNode updatedVehicle) {
        try {
            File file = new File(VEHICLES_FILE_PATH);
            if (!file.exists()) {
                log.warn("Vehicles JSON file does not exist");
                return;
            }

            ObjectNode jsonData = (ObjectNode) objectMapper.readTree(file);
            ArrayNode vehiclesArray = (ArrayNode) jsonData.get("vehicles");

            for (JsonNode node : vehiclesArray) {
                if (node instanceof ObjectNode) {
                    ObjectNode vehicle = (ObjectNode) node;
                    if (vehicleId.equals(vehicle.get("id").asText())) {
                        // 将更新后的信息替换旧的车辆信息
                        vehicle.setAll(updatedVehicle);
                        objectMapper.writeValue(file, jsonData);  // 保存更新后的数据
                        log.info("Updated vehicle with ID: {}", vehicleId);
                        return;
                    }
                }
            }
        } catch (IOException e) {
            log.error("Error updating vehicle", e);
        }
    }

    // 获取所有车辆信息
    public ArrayNode getAllVehicles() {
        try {
            File file = new File(VEHICLES_FILE_PATH);
            if (!file.exists()) {
                log.warn("Vehicles JSON file does not exist");
                return null;
            }

            ObjectNode jsonData = (ObjectNode) objectMapper.readTree(file);
            return (ArrayNode) jsonData.get("vehicles");
        } catch (IOException e) {
            log.error("Error reading vehicles data", e);
        }
        return null;
    }
}

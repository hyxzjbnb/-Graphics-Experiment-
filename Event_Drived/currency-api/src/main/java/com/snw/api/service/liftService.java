package com.snw.api.service;

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

/**
 * @author hyxzjbnb
 * @create 2024-06-14-19:30
 */
@Service
public class liftService {
    private static final Logger log = LoggerFactory.getLogger(OutboundService.class);
    private static final String LIFT_FILE_PATH = "currency-api/src/main/java/com/snw/api/json/lift.json";

    private final ObjectMapper objectMapper;

    public liftService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    // 检查和分配空闲的车辆
    public ObjectNode findAndAllocateLift(String warehouseId) {
        try {
            File file = new File(LIFT_FILE_PATH);
            if (!file.exists()) {
                log.warn("Vehicles JSON file does not exist");
                return null;
            }

            ObjectNode jsonData = (ObjectNode) objectMapper.readTree(file);
            ArrayNode vehiclesArray = (ArrayNode) jsonData.get("lift");
            List<ObjectNode> availablelift = new ArrayList<>();

            // 遍历ArrayNode来获取所有车辆
            for (JsonNode node : vehiclesArray) {
                if (node instanceof ObjectNode) {
                    ObjectNode lift = (ObjectNode) node;
                    if ("idle".equals(lift.get("status").asText())) {
                        if(warehouseId.equals(lift.get("location").asText())){
                            availablelift.add(lift);
                        }
                    }
                }
            }

            if (!availablelift.isEmpty()) {
                ObjectNode selectedVehicle = availablelift.get(0);  // 选择第一个空闲的车辆
                selectedVehicle.put("status", "in_use");  // 更新车辆状态为“使用中”
                objectMapper.writeValue(file, jsonData);  // 保存更新后的数据
                return selectedVehicle;
            }

        } catch (IOException e) {
            log.error("Error finding and allocating vehicle", e);
        }
        return null;
    }

    // 根据ID查找指定的lift信息
    public ObjectNode getLiftById(String liftId) {
        try {
            File file = new File(LIFT_FILE_PATH);
            if (!file.exists()) {
                log.warn("Lift JSON file does not exist");
                return null;
            }

            ObjectNode jsonData = (ObjectNode) objectMapper.readTree(file);
            ArrayNode liftsArray = (ArrayNode) jsonData.get("lift");

            for (JsonNode node : liftsArray) {
                if (node instanceof ObjectNode) {
                    ObjectNode lift = (ObjectNode) node;
                    if (liftId.equals(lift.get("id").asText())) {
                        return lift;
                    }
                }
            }

            log.warn("Lift with ID {} not found", liftId);
            return null;
        } catch (IOException e) {
            log.error("Error finding lift by ID", e);
            return null;
        }
    }

    // 更新指定ID的lift的状态
    public ObjectNode updateLiftStatus(String liftId, String newStatus) {
        try {
            File file = new File(LIFT_FILE_PATH);
            if (!file.exists()) {
                log.warn("Lift JSON file does not exist");
                return null;
            }

            ObjectNode jsonData = (ObjectNode) objectMapper.readTree(file);
            ArrayNode liftsArray = (ArrayNode) jsonData.get("lift");

            for (JsonNode node : liftsArray) {
                if (node instanceof ObjectNode) {
                    ObjectNode lift = (ObjectNode) node;
                    if (liftId.equals(lift.get("id").asText())) {
                        lift.put("status", newStatus);  // 更新lift的状态
                        objectMapper.writeValue(file, jsonData);  // 保存更新后的数据
                        log.info("Updated lift with ID {} to new status {}", liftId, newStatus);
                        return lift;
                    }
                }
            }

            log.warn("Lift with ID {} not found", liftId);
            return null;
        } catch (IOException e) {
            log.error("Error updating lift status", e);
            return null;
        }
    }

    // 获取所有车辆信息
    public ArrayNode getAlllifts() {
        try {
            File file = new File(LIFT_FILE_PATH);
            if (!file.exists()) {
                log.warn("Vehicles JSON file does not exist");
                return null;
            }

            ObjectNode jsonData = (ObjectNode) objectMapper.readTree(file);
            return (ArrayNode) jsonData.get("lift");
        } catch (IOException e) {
            log.error("Error reading vehicles data", e);
        }
        return null;
    }

    // 增加新车辆
    public void addLift(ObjectNode newVehicle) {
        try {
            File file = new File(LIFT_FILE_PATH);
            if (!file.exists()) {
                log.warn("Vehicles JSON file does not exist");
                return;
            }

            ObjectNode jsonData = (ObjectNode) objectMapper.readTree(file);
            ArrayNode vehiclesArray = (ArrayNode) jsonData.get("lift");

            vehiclesArray.add(newVehicle);
            objectMapper.writeValue(file, jsonData);  // 保存更新后的数据
            log.info("Added new vehicle: {}", newVehicle);
        } catch (IOException e) {
            log.error("Error adding vehicle", e);
        }
    }

    // 删除车辆
    public void deleteLift(String vehicleId) {
        try {
            File file = new File(LIFT_FILE_PATH);
            if (!file.exists()) {
                log.warn("Vehicles JSON file does not exist");
                return;
            }

            ObjectNode jsonData = (ObjectNode) objectMapper.readTree(file);
            ArrayNode vehiclesArray = (ArrayNode) jsonData.get("lift");

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
}

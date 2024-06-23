package com.snw.api.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.snw.api.event.StorageStartedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class liftService {

    private static final Logger log = LoggerFactory.getLogger(liftService.class);
    private static final String LIFT_FILE_PATH = "/Users/mac/Desktop/automated-warehouse-management-system_副本/Event_Drived/currency-api/src/main/java/com/snw/api/json/lift.json";  // 更新路径为你的实际路径
    private static final ExecutorService executor = Executors.newCachedThreadPool();

    private final ObjectMapper objectMapper;

    public liftService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ArrayNode getAllLifts() {
        try {
            File file = new File(LIFT_FILE_PATH);
            if (!file.exists()) {
                log.warn("Lift JSON file does not exist");
                return objectMapper.createArrayNode();
            }
            return (ArrayNode) objectMapper.readTree(file).get("lift");
        } catch (IOException e) {
            log.error("Error reading lifts", e);
            return objectMapper.createArrayNode();
        }
    }

    public void addLift(ObjectNode newLift) {
        try {
            File file = new File(LIFT_FILE_PATH);
            ObjectNode jsonData;

            if (!file.exists()) {
                jsonData = objectMapper.createObjectNode();
                jsonData.set("lift", objectMapper.createArrayNode());
                log.warn("Lift JSON file does not exist, creating new one.");
            } else {
                jsonData = (ObjectNode) objectMapper.readTree(file);
            }

            ArrayNode liftsArray = (ArrayNode) jsonData.get("lift");
            liftsArray.add(newLift);
            objectMapper.writeValue(file, jsonData);  // 保存更新后的数据
            log.info("Added new lift: {}", newLift);
        } catch (IOException e) {
            log.error("Error adding lift", e);
        }
    }

    public void storeLift(String position) {
        log.info("Storing lift at position: " + position);
        try {
            File file = new File(LIFT_FILE_PATH);
            ObjectNode jsonData;

            if (!file.exists()) {
                jsonData = objectMapper.createObjectNode();
                jsonData.set("lift", objectMapper.createArrayNode());
                log.warn("Lift JSON file does not exist, creating new one.");
            } else {
                jsonData = (ObjectNode) objectMapper.readTree(file);
            }

            ArrayNode liftsArray = (ArrayNode) jsonData.get("lift");

            ObjectNode newRecord = objectMapper.createObjectNode();
            newRecord.put("id", "LIFT" + System.currentTimeMillis());
            newRecord.put("position", position);
            newRecord.put("status", "loading");
            newRecord.put("creationTime", DateTimeFormatter.ISO_INSTANT.format(Instant.now()));

            liftsArray.add(newRecord);
            objectMapper.writeValue(file, jsonData);  // 保存更新后的数据
            log.info("Created lift record at position {}", position);
        } catch (IOException e) {
            log.error("Error creating lift record", e);
        }
    }

    public void deleteLift(String liftId) {
        try {
            File file = new File(LIFT_FILE_PATH);
            if (!file.exists()) {
                log.warn("Lift JSON file does not exist");
                return;
            }

            ObjectNode jsonData = (ObjectNode) objectMapper.readTree(file);
            ArrayNode liftsArray = (ArrayNode) jsonData.get("lift");

            for (int i = 0; i < liftsArray.size(); i++) {
                JsonNode node = liftsArray.get(i);
                if (node instanceof ObjectNode) {
                    ObjectNode record = (ObjectNode) node;
                    if (liftId.equals(record.get("id").asText())) {
                        liftsArray.remove(i);
                        objectMapper.writeValue(file, jsonData);  // 保存更新后的数据
                        log.info("Deleted lift with ID {}", liftId);
                        return;
                    }
                }
            }

            log.warn("Lift with ID {} not found", liftId);
        } catch (IOException e) {
            log.error("Error deleting lift", e);
        }
    }

    public ObjectNode updateLiftStatusToDown(String liftId) {
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
                    ObjectNode record = (ObjectNode) node;
                    if (liftId.equals(record.get("id").asText())) {
                        record.put("status", "down");  // 更新状态为 "down"
                        objectMapper.writeValue(file, jsonData);  // 保存更新后的数据
                        log.info("Updated lift record with ID {} to status 'down'", liftId);
                        return record;
                    }
                }
            }

            log.warn("Lift record with ID {} not found", liftId);
            return null;
        } catch (IOException e) {
            log.error("Error updating lift status to 'down'", e);
            return null;
        }
    }

    // 新增的 handleStorageStarted 方法
    public void handleStorageStarted(StorageStartedEvent event) {
        log.info("Handling storage started event: {}", event);

        // 处理存储开始事件的逻辑
        storeLift(event.getPosition());
    }

    // 新增的 findAndAllocateLift 方法
    public ObjectNode findAndAllocateLift(String position) {
        try {
            File file = new File(LIFT_FILE_PATH);
            if (!file.exists()) {
                log.warn("Lift JSON file does not exist");
                return null;
            }

            ObjectNode jsonData = (ObjectNode) objectMapper.readTree(file);
            ArrayNode liftsArray = (ArrayNode) jsonData.get("lift");

            if (liftsArray == null) {
                log.warn("No lifts found in the JSON file");
                return null;
            }

            for (JsonNode node : liftsArray) {
                if (node instanceof ObjectNode) {
                    ObjectNode lift = (ObjectNode) node;
                    if ("idle".equals(lift.get("status").asText())) {
                        lift.put("status", "allocated");
                        lift.put("position", position);
                        objectMapper.writeValue(file, jsonData);  // 保存更新后的数据
                        log.info("Allocated lift with ID {} to position {}", lift.get("id").asText(), position);
                        return lift;
                    }
                }
            }

            log.warn("No idle lift found to allocate");
            return null;
        } catch (IOException e) {
            log.error("Error allocating lift", e);
            return null;
        }
    }

    // 新增的 updateLiftStatus 方法
    public void updateLiftStatus(String liftId, String status) {
        try {
            File file = new File(LIFT_FILE_PATH);
            if (!file.exists()) {
                log.warn("Lift JSON file does not exist");
                return;
            }

            ObjectNode jsonData = (ObjectNode) objectMapper.readTree(file);
            ArrayNode liftsArray = (ArrayNode) jsonData.get("lift");

            for (JsonNode node : liftsArray) {
                if (node instanceof ObjectNode) {
                    ObjectNode lift = (ObjectNode) node;
                    if (liftId.equals(lift.get("id").asText())) {
                        lift.put("status", status);
                        objectMapper.writeValue(file, jsonData);  // 保存更新后的数据
                        log.info("Updated lift status with ID {} to {}", liftId, status);
                        return;
                    }
                }
            }

            log.warn("Lift with ID {} not found", liftId);
        } catch (IOException e) {
            log.error("Error updating lift status", e);
        }
    }

    // 新增的 getLiftById 方法
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
                        log.info("Found lift with ID {}", liftId);
                        return lift;
                    }
                }
            }

            log.warn("Lift with ID {} not found", liftId);
            return null;
        } catch (IOException e) {
            log.error("Error getting lift by ID", e);
            return null;
        }
    }

    // 计算欧几里得距离
    private double calculateEuclideanDistance(String position1, String position2) {
        String[] pos1 = position1.split(",");
        String[] pos2 = position2.split(",");
        int x1 = Integer.parseInt(pos1[0]);
        int y1 = Integer.parseInt(pos1[1]);
        int z1 = Integer.parseInt(pos1[2]);
        int x2 = Integer.parseInt(pos2[0]);
        int y2 = Integer.parseInt(pos2[1]);
        int z2 = Integer.parseInt(pos2[2]);

        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2) + Math.pow(z2 - z1, 2));
    }

    // 模拟升降机移动
    public void simulateLiftMovement(ObjectNode lift, String position) {
        executor.submit(() -> {
            try {
                String currentPosition = lift.get("location").asText();
                double distance = calculateEuclideanDistance(currentPosition, position);
                int speed = lift.get("speed").asInt();
                long travelTime = (long) (distance / speed * 2 * 1000); // 时间单位为毫秒

                // 更新升降机状态为 busy
                updateLiftStatus(lift.get("id").asText(), "busy");

                // 等待升降机完成任务
                Thread.sleep(travelTime);

                // 任务完成，更新升降机位置和状态
                lift.put("location", position);
                updateLiftStatus(lift.get("id").asText(), "idle");

                log.info("Lift with ID {} moved to position {} and is now idle", lift.get("id").asText(), position);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("Lift movement simulation interrupted", e);
            }
        });
    }
}

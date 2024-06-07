package com.snw.api.service;

/**
 * @author hyxzjbnb
 * @create 2024-06-07-20:18
 */
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.snw.api.event.GoodsArrivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class InboundService {

    private static final Logger log = LoggerFactory.getLogger(InboundService.class);
    private static final String INBOUND_FILE_PATH = "currency-api/src/main/java/com/snw/api/json/inbound.json";  // inbound JSON文件路径

    private final ObjectMapper objectMapper;

    public InboundService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    // 保存GoodsArrived事件到inbound.json文件
    public void saveGoodsArrivedEvent(GoodsArrivedEvent event) {
        try {
            File file = new File(INBOUND_FILE_PATH);
            log.info("Attempting to write to file: " + file.getAbsolutePath());

            ObjectNode jsonData;

            if (file.exists()) {
                log.info("File exists, reading current data.");
                jsonData = (ObjectNode) objectMapper.readTree(file);
            } else {
                log.info("File does not exist, creating new structure.");
                jsonData = objectMapper.createObjectNode();
                jsonData.putArray("inbound");
            }

            // 更新inbound部分
            ObjectNode newInbound = objectMapper.createObjectNode();
            newInbound.put("id", generateInboundId(jsonData));  // 生成唯一ID
            newInbound.put("shipmentId", event.getShipmentId());
            newInbound.put("warehouseId", event.getWarehouseId());
            newInbound.put("itemId", event.getItemId());
            newInbound.put("quantity", event.getQuantity());
            newInbound.put("arrivalTime", event.getArrivalTime());
            newInbound.put("status", "requesting_storage");

            // 添加文档信息（此处置空）
            ObjectNode documents = objectMapper.createObjectNode();
            documents.put("invoice", "");
            documents.put("billOfLading", "");
            documents.put("warranty", "");
            newInbound.set("documents", documents);

            // 添加位置信息
            newInbound.put("location", "logistic_point");
            newInbound.put("creationTime", event.getArrivalTime());

            jsonData.withArray("inbound").add(newInbound);

            // 使用文件输出流写入数据
            try (FileOutputStream fos = new FileOutputStream(file, false)) {
                objectMapper.writeValue(fos, jsonData);
                fos.flush(); // 确保数据被写入文件
                log.info("Successfully written to file: " + file.getAbsolutePath());
            } catch (IOException e) {
                log.error("Error writing to file: " + file.getAbsolutePath(), e);
            }

        } catch (IOException e) {
            log.error("Error handling GoodsArrived event for JSON", e);
        }
    }

    // 生成唯一的inbound ID
    private String generateInboundId(ObjectNode jsonData) {
        int maxId = jsonData.withArray("inbound").findValuesAsText("id").stream()
                .mapToInt(id -> Integer.parseInt(id.replace("IN", "")))
                .max()
                .orElse(0);
        return String.format("IN%03d", maxId + 1);
    }

    // 更新指定 shipmentId 的状态
    public void updateInboundStatus(String inboundId, String newStatus) {
        try {
            File file = new File(INBOUND_FILE_PATH);
            if (!file.exists()) {
                log.error("File not found: " + file.getAbsolutePath());
                return;
            }

            ObjectNode jsonData = (ObjectNode) objectMapper.readTree(file);
            boolean updated = false;

            // 查找并更新指定 ID 的状态
            for (JsonNode inboundNode : jsonData.withArray("inbound")) {
                if (inboundNode.get("shipmentId").asText().equals(inboundId)) {
                    ((ObjectNode) inboundNode).put("status", newStatus);
                    log.info("Updated status for ID: " + inboundId + " to " + newStatus);
                    updated = true;
                    break;
                }
            }

            if (updated) {
                // 将更新后的数据写回文件
                try (FileOutputStream fos = new FileOutputStream(file, false)) {
                    objectMapper.writeValue(fos, jsonData);
                    fos.flush(); // 确保数据被写入文件
                    log.info("Successfully updated file: " + file.getAbsolutePath());
                } catch (IOException e) {
                    log.error("Error writing to file: " + file.getAbsolutePath(), e);
                }
            } else {
                log.info("No record found for ID: " + inboundId);
            }
        } catch (IOException e) {
            log.error("Error updating status in JSON", e);
        }
    }
    // 将指定 ID 的状态更新为 "unloading"
    public void startUnloading(String inboundId) {
        updateInboundStatus(inboundId, "unloading");
    }

    // 将指定 ID 的状态更新为 "completed"
    public void completeUnloading(String inboundId) {
        updateInboundStatus(inboundId, "completed");
    }

}


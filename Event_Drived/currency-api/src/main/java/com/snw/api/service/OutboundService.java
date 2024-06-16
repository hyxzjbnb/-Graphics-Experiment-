package com.snw.api.service;

/**
 * @author hyxzjbnb
 * @create 2024-06-14-19:03
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
import java.time.Instant;
import java.time.format.DateTimeFormatter;

@Service
public class OutboundService {

    private static final Logger log = LoggerFactory.getLogger(OutboundService.class);
    private static final String OUTBOUND_FILE_PATH = "currency-api/src/main/java/com/snw/api/json/outbound.json";  // Outbound JSON文件路径

    private final ObjectMapper objectMapper;

    public OutboundService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    // 生成出库记录
    public void createOutboundRecord(String orderId, String itemId, int quantity) {
        try {
            File file = new File(OUTBOUND_FILE_PATH);
            ObjectNode jsonData;

            if (!file.exists()) {
                jsonData = objectMapper.createObjectNode();
                jsonData.set("outbound", objectMapper.createArrayNode());
                log.warn("Outbound JSON file does not exist, creating new one.");
            } else {
                jsonData = (ObjectNode) objectMapper.readTree(file);
            }

            ArrayNode outboundArray = (ArrayNode) jsonData.get("outbound");

            ObjectNode newRecord = objectMapper.createObjectNode();
            newRecord.put("id", "OUT" + System.currentTimeMillis());
            newRecord.put("orderId", orderId);
            newRecord.put("itemId", itemId);
            newRecord.put("quantity", quantity);
            newRecord.put("status", "loading");
            newRecord.put("creationTime", DateTimeFormatter.ISO_INSTANT.format(Instant.now()));

            // 添加默认的文档信息（可以根据实际需求调整）
            ObjectNode documents = objectMapper.createObjectNode();
            documents.put("invoice", "INV" + System.currentTimeMillis());
            documents.put("billOfLading", "BL" + System.currentTimeMillis());
            documents.put("warranty", "WT" + System.currentTimeMillis());
            newRecord.set("documents", documents);

            outboundArray.add(newRecord);
            objectMapper.writeValue(file, jsonData);  // 保存更新后的数据
            log.info("Created outbound record for order {}: item {} quantity {}", orderId, itemId, quantity);
        } catch (IOException e) {
            log.error("Error creating outbound record", e);
        }
    }

    // 修改指定出库记录的状态为 "down"
    public ObjectNode updateOutboundStatusToDown(String outboundId) {
        try {
            File file = new File(OUTBOUND_FILE_PATH);
            if (!file.exists()) {
                log.warn("Outbound JSON file does not exist");
                return null;
            }

            ObjectNode jsonData = (ObjectNode) objectMapper.readTree(file);
            ArrayNode outboundArray = (ArrayNode) jsonData.get("outbound");

            for (JsonNode node : outboundArray) {
                if (node instanceof ObjectNode) {
                    ObjectNode record = (ObjectNode) node;
                    if (outboundId.equals(record.get("orderId").asText())) {
                        record.put("status", "down");  // 更新状态为 "down"
                        objectMapper.writeValue(file, jsonData);  // 保存更新后的数据
                        log.info("Updated outbound record with ID {} to status 'down'", outboundId);
                        return record;
                    }
                }
            }

            log.warn("Outbound record with ID {} not found", outboundId);
            return null;
        } catch (IOException e) {
            log.error("Error updating outbound status to 'down'", e);
            return null;
        }
    }
}

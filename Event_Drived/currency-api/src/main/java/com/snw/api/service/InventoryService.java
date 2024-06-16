//package com.snw.api.service;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.node.ArrayNode;
//import com.fasterxml.jackson.databind.node.ObjectNode;
//import com.snw.api.event.InspectionCompletedEvent;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import java.io.File;
//import java.io.IOException;
///**
// * @author hyxzjbnb
// * @create 2024-06-14-19:02
// */
//
//@Service
//public class InventoryService {
//
//    private static final Logger log = LoggerFactory.getLogger(InventoryService.class);
//    private static final String INVENTORY_FILE_PATH = "currency-api/src/main/java/com/snw/api/json/inventory.json";  // Inventory JSON文件路径
//
//    private final ObjectMapper objectMapper;
//
//    public InventoryService(ObjectMapper objectMapper) {
//        this.objectMapper = objectMapper;
//    }
//
//    // 检查库存是否足够
//    public ObjectNode isStockAvailable(String itemId, int requiredQuantity) {
//        try {
//            File file = new File(INVENTORY_FILE_PATH);
//            if (!file.exists()) {
//                log.warn("Inventory JSON file does not exist");
//                return null;
//            }
//
//            ObjectNode jsonData = (ObjectNode) objectMapper.readTree(file);
//            ArrayNode inventoryArray = (ArrayNode) jsonData.get("inventory");
//
//            for (JsonNode node : inventoryArray) {
//                if (node instanceof ObjectNode) {
//                    ObjectNode item = (ObjectNode) node;
//                    if (itemId.equals(item.get("itemId").asText())) {
//                        int currentStock = item.get("quantity").asInt();
//                        if(currentStock >= requiredQuantity){
//                            return item;
//                        }else{
//                            return null;
//                        }
//                    }
//                }
//            }
//        } catch (IOException e) {
//            log.error("Error checking stock availability", e);
//        }
//        return null;
//    }
//
//    // 减少库存
//    public void decreaseStock(String itemId, int quantity) {
//        try {
//            File file = new File(INVENTORY_FILE_PATH);
//            if (!file.exists()) {
//                log.warn("Inventory JSON file does not exist");
//                return;
//            }
//
//            ObjectNode jsonData = (ObjectNode) objectMapper.readTree(file);
//            ArrayNode inventoryArray = (ArrayNode) jsonData.get("inventory");
//
//            for (JsonNode node : inventoryArray) {
//                if (node instanceof ObjectNode) {
//                    ObjectNode item = (ObjectNode) node;
//                    if (itemId.equals(item.get("itemId").asText())) {
//                        int currentStock = item.get("quantity").asInt();
//                        item.put("quantity", currentStock - quantity);
//                        objectMapper.writeValue(file, jsonData);  // 保存更新后的数据
//                        log.info("Stock updated for item {}: new quantity {}", itemId, currentStock - quantity);
//                        return;
//                    }
//                }
//            }
//        } catch (IOException e) {
//            log.error("Error decreasing stock", e);
//        }
//    }
//    // 添加库存
//    public void addInventory(InspectionCompletedEvent event) {
//        try {
//            File file = new File(INVENTORY_FILE_PATH);
//            if (!file.exists()) {
//                log.warn("Inventory JSON file does not exist");
//                return;
//            }
//
//            ObjectNode jsonData = (ObjectNode) objectMapper.readTree(file);
//            ArrayNode inventoryArray = (ArrayNode) jsonData.get("inventory");
//
//            ObjectNode newItem = objectMapper.createObjectNode();
//            newItem.put("id", "IV" + System.currentTimeMillis()); // 生成一个新的ID
//            newItem.put("shipmentId", event.getShipmentId());
//            newItem.put("itemId", event.getItemId());
//            newItem.put("quantity", event.getQuantity());
//            newItem.set("documents", objectMapper.valueToTree(event.getDocuments()));
//            newItem.put("status", event.getStatus());
//            newItem.put("location", event.getLocation());
//            newItem.set("warehouseLocation", objectMapper.valueToTree(event.getWarehouseLocation()));
//            newItem.put("creationTime", event.getCreationTime());
//
//            inventoryArray.add(newItem);
//            objectMapper.writeValue(file, jsonData);  // 保存更新后的数据
//            log.info("New inventory item added: {}", newItem);
//        } catch (IOException e) {
//            log.error("Error adding inventory", e);
//        }
//    }
//}
package com.snw.api.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.snw.api.event.StorageCompletedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**
 * @create 2024-06-14-19:02
 */
@Service
public class InventoryService {

    private static final Logger log = LoggerFactory.getLogger(InventoryService.class);
    private static final String INVENTORY_FILE_PATH = "currency-api/src/main/java/com/snw/api/json/inventory.json";  // Inventory JSON文件路径

    private final ObjectMapper objectMapper;

    public InventoryService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ObjectNode isStockAvailable(String itemId, int requiredQuantity) {
        try {
            File file = new File(INVENTORY_FILE_PATH);
            if (!file.exists()) {
                log.warn("Inventory JSON file does not exist");
                return null;
            }

            ObjectNode jsonData = (ObjectNode) objectMapper.readTree(file);
            ArrayNode inventoryArray = (ArrayNode) jsonData.get("inventory");

            for (JsonNode node : inventoryArray) {
                if (node instanceof ObjectNode) {
                    ObjectNode item = (ObjectNode) node;
                    if (itemId.equals(item.get("itemId").asText())) {
                        int currentStock = item.get("quantity").asInt();
                        if(currentStock >= requiredQuantity){
                            return item;
                        }else{
                            return null;
                        }
                    }
                }
            }
        } catch (IOException e) {
            log.error("Error checking stock availability", e);
        }
        return null;
    }

    public void decreaseStock(String itemId, int quantity) {
        try {
            File file = new File(INVENTORY_FILE_PATH);
            if (!file.exists()) {
                log.warn("Inventory JSON file does not exist");
                return;
            }

            ObjectNode jsonData = (ObjectNode) objectMapper.readTree(file);
            ArrayNode inventoryArray = (ArrayNode) jsonData.get("inventory");

            for (JsonNode node : inventoryArray) {
                if (node instanceof ObjectNode) {
                    ObjectNode item = (ObjectNode) node;
                    if (itemId.equals(item.get("itemId").asText())) {
                        int currentStock = item.get("quantity").asInt();
                        item.put("quantity", currentStock - quantity);
                        objectMapper.writeValue(file, jsonData);
                        log.info("Stock updated for item {}: new quantity {}", itemId, currentStock - quantity);
                        return;
                    }
                }
            }
        } catch (IOException e) {
            log.error("Error decreasing stock", e);
        }
    }

    public void addInventory(StorageCompletedEvent event) {
        try {
            File file = new File(INVENTORY_FILE_PATH);
            if (!file.exists()) {
                log.warn("Inventory JSON file does not exist");
                return;
            }

            ObjectNode jsonData = (ObjectNode) objectMapper.readTree(file);
            ArrayNode inventoryArray = (ArrayNode) jsonData.get("inventory");

            for (JsonNode node : inventoryArray) {
                if (node instanceof ObjectNode) {
                    ObjectNode item = (ObjectNode) node;
                    if (event.getShipmentId().equals(item.get("shipmentId").asText())) {
                        item.put("status", "stored");
                        item.set("warehouseLocation", objectMapper.valueToTree(event.getStorageLocation()));
                        item.put("completionTime", event.getCompletionTime());
                        objectMapper.writeValue(file, jsonData);
                        log.info("Inventory updated for shipment {}: new status {}", event.getShipmentId(), "stored");
                        return;
                    }
                }
            }
        } catch (IOException e) {
            log.error("Error adding inventory", e);
        }
    }
}

package com.snw.api.service;

/**
 * @author hyxzjbnb
 * @create 2024-06-07-20:41
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
public class WarehouseService {

    private static final Logger log = LoggerFactory.getLogger(WarehouseService.class);
    private static final String WAREHOUSE_FILE_PATH = "currency-api/src/main/java/com/snw/api/json/warehouse.json";  // warehouse JSON文件路径

    private final ObjectMapper objectMapper;

    public WarehouseService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    // 检查和分配有足够空间的仓库
    public ObjectNode findAndAllocateWarehouse(int totalQuantity) {
        try {
            File file = new File(WAREHOUSE_FILE_PATH);
            if (!file.exists()) {
                log.warn("Warehouse JSON file does not exist");
                return null;
            }

            ObjectNode jsonData = (ObjectNode) objectMapper.readTree(file);
            ArrayNode warehousesArray = (ArrayNode) jsonData.get("warehouse");
            List<ObjectNode> availableWarehouses = new ArrayList<>();

            // 遍历ArrayNode来获取所有仓库
            for (JsonNode node : warehousesArray) {
                if (node instanceof ObjectNode) {
                    ObjectNode warehouse = (ObjectNode) node;
                    if (warehouse.get("availableSlots").asInt() >= totalQuantity) {
                        availableWarehouses.add(warehouse);
                    }
                }
            }

            if (!availableWarehouses.isEmpty()) {
                ObjectNode selectedWarehouse = availableWarehouses.get(0);  // 选择第一个满足条件的仓库
                int newAvailableSlots = selectedWarehouse.get("availableSlots").asInt() - totalQuantity;
                selectedWarehouse.put("availableSlots", newAvailableSlots);
                objectMapper.writeValue(file, jsonData);  // 保存更新后的数据
                return selectedWarehouse;
            }

        } catch (IOException e) {
            log.error("Error finding and allocating warehouse", e);
        }
        return null;
    }

    // 增加新仓库
    public void addWarehouse(ObjectNode newWarehouse) {
        try {
            File file = new File(WAREHOUSE_FILE_PATH);
            if (!file.exists()) {
                log.warn("Warehouse JSON file does not exist");
                return;
            }

            ObjectNode jsonData = (ObjectNode) objectMapper.readTree(file);
            ArrayNode warehousesArray = (ArrayNode) jsonData.get("warehouse");

            warehousesArray.add(newWarehouse);
            objectMapper.writeValue(file, jsonData);  // 保存更新后的数据
            log.info("Added new warehouse: {}", newWarehouse);
        } catch (IOException e) {
            log.error("Error adding new warehouse", e);
        }
    }

    // 修改仓库存储量和尺寸
    public void updateWarehouseCapacity(String warehouseId, int newCapacity, ObjectNode newDimensions) {
        try {
            File file = new File(WAREHOUSE_FILE_PATH);
            if (!file.exists()) {
                log.warn("Warehouse JSON file does not exist");
                return;
            }

            ObjectNode jsonData = (ObjectNode) objectMapper.readTree(file);
            ArrayNode warehousesArray = (ArrayNode) jsonData.get("warehouse");

            for (JsonNode node : warehousesArray) {
                if (node instanceof ObjectNode) {
                    ObjectNode warehouse = (ObjectNode) node;
                    if (warehouseId.equals(warehouse.get("id").asText())) {
                        warehouse.put("availableSlots", newCapacity);
                        warehouse.set("dimensions", newDimensions);
                        objectMapper.writeValue(file, jsonData);  // 保存更新后的数据
                        log.info("Updated warehouse {} with new capacity {} and new dimensions {}", warehouseId, newCapacity, newDimensions);
                        return;
                    }
                }
            }
        } catch (IOException e) {
            log.error("Error updating warehouse capacity", e);
        }
    }

    // 获取所有仓库的信息
    public ArrayNode getAllWarehouses() {
        try {
            File file = new File(WAREHOUSE_FILE_PATH);
            if (!file.exists()) {
                log.warn("Warehouse JSON file does not exist");
                return null;
            }

            ObjectNode jsonData = (ObjectNode) objectMapper.readTree(file);
            return (ArrayNode) jsonData.get("warehouse");
        } catch (IOException e) {
            log.error("Error getting all warehouses", e);
            return null;
        }
    }
    // 删除仓库
    public void deleteWarehouse(String warehouseId) {
        try {
            File file = new File(WAREHOUSE_FILE_PATH);
            if (!file.exists()) {
                log.warn("Warehouse JSON file does not exist");
                return;
            }

            ObjectNode jsonData = (ObjectNode) objectMapper.readTree(file);
            ArrayNode warehousesArray = (ArrayNode) jsonData.get("warehouse");

            // 寻找并移除指定ID的仓库
            for (int i = 0; i < warehousesArray.size(); i++) {
                JsonNode node = warehousesArray.get(i);
                if (node instanceof ObjectNode) {
                    ObjectNode warehouse = (ObjectNode) node;
                    if (warehouseId.equals(warehouse.get("id").asText())) {
                        warehousesArray.remove(i);
                        objectMapper.writeValue(file, jsonData);  // 保存更新后的数据
                        log.info("Deleted warehouse with ID: {}", warehouseId);
                        return;
                    }
                }
            }

            log.warn("Warehouse with ID {} not found", warehouseId);
        } catch (IOException e) {
            log.error("Error deleting warehouse", e);
        }
    }

    // 查找指定ID的仓库信息
    public ObjectNode getWarehouseById(String warehouseId) {
        try {
            File file = new File(WAREHOUSE_FILE_PATH);
            if (!file.exists()) {
                log.warn("Warehouse JSON file does not exist");
                return null;
            }

            ObjectNode jsonData = (ObjectNode) objectMapper.readTree(file);
            ArrayNode warehousesArray = (ArrayNode) jsonData.get("warehouse");

            for (JsonNode node : warehousesArray) {
                if (node instanceof ObjectNode) {
                    ObjectNode warehouse = (ObjectNode) node;
                    if (warehouseId.equals(warehouse.get("id").asText())) {
                        return warehouse;
                    }
                }
            }

            log.warn("Warehouse with ID {} not found", warehouseId);
            return null;
        } catch (IOException e) {
            log.error("Error finding warehouse by ID", e);
            return null;
        }
    }
}

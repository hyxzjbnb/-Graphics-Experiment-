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
}

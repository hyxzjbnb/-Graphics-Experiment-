package com.snw.api.service;

import com.snw.api.event.StorageLocationAllocatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class StorageService {

    private static final Logger log = LoggerFactory.getLogger(StorageService.class);
    private List<Map<String, Object>> warehouse;

    public void allocateStorageLocation(StorageLocationAllocatedEvent event) {
        log.info("Allocating storage location: {}", event);

        String id = event.getId();
        int capacity = event.getCapacity();
        Map<String, Integer> location = findAvailableSlot(warehouse, event.getDimensions());

        if (location == null) {
            log.warn("No available slot found for id: {}", id);
            return;
        }

        log.info("Storage location allocated for id: {} with capacity: {} at location: {}", id, capacity, location);
    }

    private Map<String, Integer> findAvailableSlot(List<Map<String, Object>> warehouses, Map<String, String> dimensions) {
        for (Map<String, Object> warehouse : warehouses) {
            int availableSlots = (int) warehouse.get("availableSlots");
            if (availableSlots > 0) {
                Map<String, Integer> location = allocateSlot((Map<String, String>) warehouse.get("dimensions"));
                if (location != null) {
                    warehouse.put("availableSlots", availableSlots - 1);
                    return location;
                }
            }
        }
        return null;
    }

    private Map<String, Integer> allocateSlot(Map<String, String> dimensions) {
        int x = Integer.parseInt(dimensions.get("x"));
        int y = Integer.parseInt(dimensions.get("y"));
        int z = Integer.parseInt(dimensions.get("z"));

        for (int i = z; i >= 0; i--) {
            for (int j = y; j >= 0; j--) {
                for (int k = x; k >= 0; k--) {
                    // Check if the slot is available (logic to be implemented)
                    // Assuming all slots are available for the sake of example
                    return Map.of("x", k, "y", j, "z", i);
                }
            }
        }
        return null;
    }
}

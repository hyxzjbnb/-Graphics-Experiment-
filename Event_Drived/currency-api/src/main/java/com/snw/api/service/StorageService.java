package com.snw.api.service;

import com.snw.api.event.StorageLocationAllocatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class StorageService {

    private static final Logger log = LoggerFactory.getLogger(StorageService.class);

    public void allocateStorageLocation(StorageLocationAllocatedEvent event) {
        log.info("Allocating storage location: {}", event);

        // 假设在这里进行一些逻辑处理
        int capacity = event.getCapacity();
        String id = event.getId();
        // 处理逻辑...

        log.info("Storage location allocated for id: {} with capacity: {}", id, capacity);
    }
}

package com.snw.api.event;

import java.util.Map;

public class StorageLocationAllocatedEvent {
    private String status;
    private String id;
    private int capacity;
    private Map<String, Integer> location;
    private Map<String, String> dimensions;

    public StorageLocationAllocatedEvent(String status, String id, int capacity, Map<String, Integer> location) {
        this.status = status;
        this.id = id;
        this.capacity = capacity;
        this.location = location;
        this.dimensions = dimensions;
    }

    public String getStatus() {
        return status;
    }

    public String getId() {
        return id;
    }

    public int getCapacity() {
        return capacity;
    }

    public Map<String, Integer> getLocation() {
        return location;
    }

    public Map<String, String> getDimensions() {
        return dimensions;
    }
}

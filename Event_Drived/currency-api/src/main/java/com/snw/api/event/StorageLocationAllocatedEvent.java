package com.snw.api.event;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
@Builder
public class StorageLocationAllocatedEvent {
    private String status;
    private String id;
    private int capacity;
    private Map<String, Integer> location;

    public StorageLocationAllocatedEvent(String status) {
        this.status = status;
    }

    public StorageLocationAllocatedEvent(String status, String id, int capacity, Map<String, Integer> location) {
        this.status = status;
        this.id = id;
        this.capacity = capacity;
        this.location = location;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getId() {
        return id;
    }
}

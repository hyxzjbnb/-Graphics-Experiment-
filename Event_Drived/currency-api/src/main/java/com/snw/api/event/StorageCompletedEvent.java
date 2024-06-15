package com.snw.api.event;

import lombok.*;

import java.util.Map;

/**
 * @create 2024-06-14-19:30
 */
@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
@Builder
public class StorageCompletedEvent {
    private String shipmentId;
    private String warehouseId;
    private String completionTime;
    private Map<String, Integer> storageLocation;

    public StorageCompletedEvent(String shipmentId, String warehouseId, String completionTime, Map<String, Integer> storageLocation) {
        this.shipmentId = shipmentId;
        this.warehouseId = warehouseId;
        this.completionTime = completionTime;
        this.storageLocation = storageLocation;
    }
}

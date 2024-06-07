package com.snw.api.event;

import lombok.*;

/**
 * @author hyxzjbnb
 * @create 2024-06-07-17:34
 */
@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
@Builder
public class UnloadingStartedEvent {
    private String shipmentId;
    private String warehouseId;
    private String startTime;

    public UnloadingStartedEvent(String shipmentId, String warehouseId, String startTime) {
        this.shipmentId = shipmentId;
        this.warehouseId = warehouseId;
        this.startTime = startTime;
    }

    // Getters and setters
    // toString method for logging
}

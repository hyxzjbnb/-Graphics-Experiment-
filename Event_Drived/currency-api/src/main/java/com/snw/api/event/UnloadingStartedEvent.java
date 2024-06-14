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
    private String vehicleId;

    public UnloadingStartedEvent(String shipmentId, String warehouseId, String startTime,String vehicleId) {
        this.shipmentId = shipmentId;
        this.warehouseId = warehouseId;
        this.startTime = startTime;
        this.vehicleId = vehicleId;
    }

    // Getters and setters
    // toString method for logging
}

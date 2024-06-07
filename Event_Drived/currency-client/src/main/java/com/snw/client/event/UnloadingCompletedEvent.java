package com.snw.client.event;

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
public class UnloadingCompletedEvent {
    private String shipmentId;
    private String warehouseId;
    private String completionTime;
    private String vehicleId;

    public UnloadingCompletedEvent(String shipmentId, String warehouseId, String completionTime,String vehicleId) {
        this.shipmentId = shipmentId;
        this.warehouseId = warehouseId;
        this.completionTime = completionTime;
        this.vehicleId = vehicleId;
    }

    // Getters and setters
    // toString method for logging
}
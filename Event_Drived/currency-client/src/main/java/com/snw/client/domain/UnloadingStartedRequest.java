package com.snw.client.domain;

import lombok.*;

/**
 * @author hyxzjbnb
 * @create 2024-06-07-22:42
 */
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class UnloadingStartedRequest {
    private String shipmentId;
    private String warehouseId;
    private String startTime;
    private String vehicleId;

    // Getters and setters
    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}

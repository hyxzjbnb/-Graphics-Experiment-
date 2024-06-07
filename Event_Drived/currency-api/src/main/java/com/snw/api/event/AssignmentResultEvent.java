package com.snw.api.event;

import lombok.*;

/**
 * @author hyxzjbnb
 * @create 2024-06-07-21:17
 */
@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
@Builder
public class AssignmentResultEvent {
    private String shipmentId;
    private String warehouseId;
    private String vehicleId;
    private String status; // success 或 failure
    private int travelTime;

    public AssignmentResultEvent(String shipmentId, String warehouseId, String vehicleId, String status, int travelTime) {
        this.shipmentId = shipmentId;
        this.warehouseId = warehouseId;
        this.vehicleId = vehicleId;
        this.status = status;
        this.travelTime = travelTime;
    }

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

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(int travelTime) {
        this.travelTime = travelTime;
    }

    @Override
    public String toString() {
        return "AssignmentResultEvent{" +
                "shipmentId='" + shipmentId + '\'' +
                ", warehouseId='" + warehouseId + '\'' +
                ", vehicleId='" + vehicleId + '\'' +
                ", status='" + status + '\'' +
                ", travelTime=" + travelTime +
                '}';
    }
}

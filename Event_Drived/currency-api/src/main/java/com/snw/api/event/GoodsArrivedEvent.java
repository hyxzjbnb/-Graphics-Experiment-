package com.snw.api.event;

import lombok.*;

/**
 * @author hyxzjbnb
 * @create 2024-06-07-17:33
 */
@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
@Builder
public class GoodsArrivedEvent {
    private String shipmentId;
    private String warehouseId;
    private String itemId;
    private int quantity;
    private String arrivalTime;

    public GoodsArrivedEvent(String shipmentId, String warehouseId, String itemId,int quantity, String arrivalTime) {
        this.shipmentId = shipmentId;
        this.warehouseId = warehouseId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.arrivalTime = arrivalTime;
    }

    @Override
    public String toString() {
        return "GoodsArrivedEvent{" +
                "shipmentId='" + shipmentId + '\'' +
                ", warehouseId='" + warehouseId + '\'' +
                ", warehouseId='" + itemId + '\'' +
                ", quantity=" + quantity +
                ", arrivalTime='" + arrivalTime + '\'' +
                '}';
    }
}
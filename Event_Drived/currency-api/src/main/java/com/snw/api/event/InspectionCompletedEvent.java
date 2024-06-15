package com.snw.api.event;

import lombok.*;

import java.util.Map;

/**
 * @create 2024-06-14-19:02
 */
@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
@Builder
public class InspectionCompletedEvent {
    private String shipmentId;
    private String itemId;
    private int quantity;
    private Map<String, String> documents;
    private String status;
    private String location;
    private Map<String, Integer> warehouseLocation;
    private String creationTime;

    public InspectionCompletedEvent(String shipmentId, String itemId, int quantity, Map<String, String> documents,
                                    String status, String location, Map<String, Integer> warehouseLocation,
                                    String creationTime) {
        this.shipmentId = shipmentId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.documents = documents;
        this.status = status;
        this.location = location;
        this.warehouseLocation = warehouseLocation;
        this.creationTime = creationTime;
    }
}

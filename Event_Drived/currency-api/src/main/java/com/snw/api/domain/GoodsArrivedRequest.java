package com.snw.api.domain;

import lombok.*;

/**
 * @author hyxzjbnb
 * @create 2024-06-07-17:40
 */
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class GoodsArrivedRequest {
    private String shipmentId;
    private String warehouseId;
    private String itemId;
    private int quantity;
    private String arrivalTime;
    // Getters and setters
}
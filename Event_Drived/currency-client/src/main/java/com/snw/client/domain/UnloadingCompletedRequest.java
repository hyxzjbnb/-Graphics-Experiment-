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
public class UnloadingCompletedRequest {
    private String shipmentId;
    private String warehouseId;
    private String completionTime;
    private String vehicleId;

}


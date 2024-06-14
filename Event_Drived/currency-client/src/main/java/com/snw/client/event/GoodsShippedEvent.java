package com.snw.client.event;

import lombok.*;

/**
 * @author hyxzjbnb
 * @create 2024-06-14-21:48
 */
@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
@Builder
public class GoodsShippedEvent {
    private String orderId;
    private String warehouseId;

}

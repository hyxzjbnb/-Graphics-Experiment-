package com.snw.api.event;

import lombok.*;

/**
 * @author hyxzjbnb
 * @create 2024-06-14-21:48
 */
@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
public class GoodsShippedEvent {
    private String orderId;
    private String liftId;
}

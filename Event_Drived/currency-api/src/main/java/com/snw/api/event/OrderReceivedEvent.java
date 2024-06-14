package com.snw.api.event;

import lombok.*;

/**
 * @author hyxzjbnb
 * @create 2024-06-14-19:09
 */
@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
@Builder
public class OrderReceivedEvent {
    private String orderId;
    private String itemId;
    private int quantity;

    public OrderReceivedEvent(String orderId,String itemId,int quantity){
        this.orderId = orderId;
        this.itemId = itemId;
        this.quantity = quantity;
    }

}

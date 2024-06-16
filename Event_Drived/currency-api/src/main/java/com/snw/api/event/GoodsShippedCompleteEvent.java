package com.snw.api.event;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * @author hyxzjbnb
 * @create 2024-06-14-21:48
 */
@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
public class GoodsShippedCompleteEvent {
    private String orderId;
    private String vehicleId;


    public GoodsShippedCompleteEvent(String orderId, String vehicleId){
        this.orderId = orderId;
        this.vehicleId = vehicleId;
    }
}

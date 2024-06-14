package com.snw.api.event;

import lombok.*;

/**
 * @author hyxzjbnb
 * @create 2024-06-07-23:35
 */
@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
public class OrderPostResult {
    private int time;
    private String status; // success 或 failure
    private String liftId;

    public OrderPostResult(String status, int time,String liftId){
        this.status = status;
        this.time = time;
        this.liftId = liftId;
    }
}

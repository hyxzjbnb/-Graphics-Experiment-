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
@Builder
public class PostResult {
    private String event;
    private String status; // success 或 failure

    public PostResult(String status,String event){
        this.status = status;
        this.event = event;
    }
}

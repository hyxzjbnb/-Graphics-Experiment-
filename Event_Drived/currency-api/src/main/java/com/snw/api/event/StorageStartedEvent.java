package com.snw.api.event;

import lombok.*;

/**
 * @create 2024-06-14-19:02
 */
@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
@Builder
public class StorageStartedEvent {
    private String id;
    private String startTime;

    public StorageStartedEvent(String id, String startTime) {
        this.id = id;
        this.startTime = startTime;
    }
}

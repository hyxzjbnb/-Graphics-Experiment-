package com.snw.client.event;

import lombok.*;

import java.util.Map;

/**
 * @author hyxzjbnb
 * @create 2024-06-10-20:28
 */
@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
@Builder
public class InspectionEvent {
    private String id;
    private String inspectionTime;
    private Map<String, String> documents;

    public InspectionEvent(String id, String inspectionTime, Map<String, String> documents) {
        this.id = id;
        this.inspectionTime = inspectionTime;
        this.documents = documents;
    }
}

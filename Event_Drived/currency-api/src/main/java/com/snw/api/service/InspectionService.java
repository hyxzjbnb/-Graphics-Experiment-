package com.snw.api.service;

import com.snw.api.event.InspectionCompletedEvent;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class InspectionService {

    private static final Map<String, InspectionCompletedEvent> inspectionData = new HashMap<>();

    static {
        // 初始化一些示例数据
        inspectionData.put("IN001", new InspectionCompletedEvent(
                "SH12345", "IT001", 9840,
                Map.of("invoice", "INV12345", "billOfLading", "BL12345", "warranty", "WT12345"),
                "stored", "WH001",
                Map.of("x", 2, "y", 3, "z", 1),
                "2024-06-07T10:30:00Z"
        ));
    }

    public InspectionCompletedEvent getInspectionDetailsById(String id) {
        return inspectionData.get(id);
    }
}

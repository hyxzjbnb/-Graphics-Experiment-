package com.example.ex3_2_back.specifications;

import org.springframework.data.jpa.domain.Specification;
import com.example.ex3_2_back.entity.OutboundTask;
import java.time.LocalDateTime;
/**
 * @author hyxzjbnb
 * @create 2024-04-29-19:36
 */
public class OutPostSpecifications {
    public static Specification<OutboundTask> hasStatus(String status) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("status")), "%" + status.toLowerCase() + "%");
    }
    public static Specification<OutboundTask> hasStartDate(LocalDateTime startDate) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("createdAt"), startDate);
    }

    public static Specification<OutboundTask> hasEndDate(LocalDateTime endDate) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("createdAt"), endDate);
    }

}
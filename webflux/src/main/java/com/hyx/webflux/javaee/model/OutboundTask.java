package com.hyx.webflux.javaee.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * Represents an outbound task in the system.
 */
@Table("outbound_task")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OutboundTask {

    @Id
    private Integer outboundTaskId;

    private Order order;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // Removed the unnecessary setId method
}

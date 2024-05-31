package com.hyx.webflux.javaee.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("order-exception")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderException {

    @Id
    private Integer id;
    private Integer orderId;
    private String description;
    private LocalDateTime createdAt;
}

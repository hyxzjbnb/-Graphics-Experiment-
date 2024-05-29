package com.hyx.webflux.javaee.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * @author hyxzjbnb
 * @create 2024-05-28-22:08
 */
@Table("Inventory")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Inventory {
    @Id
    Integer id;

    private int pid;

    private Integer quantity;

    private LocalDateTime date;
}

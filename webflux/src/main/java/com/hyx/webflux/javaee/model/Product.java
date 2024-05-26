package com.hyx.webflux.javaee.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author hyxzjbnb
 * @create 2024-05-26-19:04
 */
@Table("Products")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    int id;

    private String name;

    private String description;

    private float price;

    private LocalDate Date;
}

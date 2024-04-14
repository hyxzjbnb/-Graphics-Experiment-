package com.example.ex3_2_back.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "Products")
@Schema(description = "Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "Product ID", example = "1")
    private Long productId;

    @Column(nullable = false)
    @Schema(description = "Product Name", example = "iPhone 13")
    private String name;

    @Column(nullable = false)
    @Schema(description = "Product Price", example = "999.99")
    private BigDecimal price;

    @Column(nullable = false)
    @Schema(description = "Creation Timestamp", example = "2024-04-14T12:00:00")
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @Schema(description = "Last Updated Timestamp", example = "2024-04-14T12:00:00")
    private LocalDateTime lastUpdated;
}

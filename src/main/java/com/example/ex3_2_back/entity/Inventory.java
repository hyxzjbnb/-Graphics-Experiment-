package com.example.ex3_2_back.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "Inventory")
@Schema(description = "Inventory")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "Inventory ID", example = "1")
    private Long inventoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false)
    @Schema(description = "Product ID")
    private Product product;

    @Column(nullable = false)
    @Schema(description = "Quantity", example = "100")
    private int quantity;

    @Column(nullable = false)
    @Schema(description = "Last Updated Timestamp", example = "2024-04-14T12:00:00")
    private LocalDateTime lastUpdated;
}

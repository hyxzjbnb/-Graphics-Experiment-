package com.example.ex3_2_back.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "InboundItems")
@Schema(description = "Inbound Items")
public class InboundItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "Inbound Item ID", example = "1")
    private Long inboundItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inbound_task_id", referencedColumnName = "inbound_task_id", nullable = false)
    @Schema(description = "Inbound Task ID")
    private InboundTask inboundTask;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false)
    @Schema(description = "Product ID")
    private Product product;

    @Column(nullable = false)
    @Schema(description = "Quantity", example = "10")
    private int quantity;

    @Column(nullable = false)
    @Schema(description = "Production Date", example = "2024-04-14")
    private LocalDate productionDate;
}

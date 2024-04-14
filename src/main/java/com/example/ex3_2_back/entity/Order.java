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
@Table(name = "Orders")
@Schema(description = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "Order ID", example = "1")
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    @Schema(description = "Customer ID")
    private User user;

    @Column(nullable = false)
    @Schema(description = "Order Status", example = "Pending")
    private String status;

    @Column(nullable = false)
    @Schema(description = "Creation Timestamp", example = "2024-04-14T12:00:00")
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @Schema(description = "Last Updated Timestamp", example = "2024-04-14T12:00:00")
    private LocalDateTime updatedAt;
}

package com.example.ex3_2_back.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "OutboundItems")
@Schema(description = "Outbound Items")
public class OutboundItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "Outbound Item ID", example = "1")
    private Long outboundItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "outbound_task_id", referencedColumnName = "outbound_task_id", nullable = false)
    @Schema(description = "Outbound Task ID")
    private OutboundTask outboundTask;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", referencedColumnName = "inbound_item_id", nullable = false)
    @Schema(description = "Inbound Item ID")
    private InboundItem inboundItem;

    @Column(nullable = false)
    @Schema(description = "Quantity", example = "5")
    private int quantity;
}

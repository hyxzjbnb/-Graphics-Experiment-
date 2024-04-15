package com.example.ex3_2_back.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "OutboundItems")
public class OutboundItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    Integer outboundItemId;

    @ManyToOne
    @JoinColumn(name = "outboundTaskId", nullable = false)
    private OutboundTask outboundTask;

    @ManyToOne
    @JoinColumn(name = "itemId", nullable = false)
    private InboundItem inboundItem;

    @Column(nullable = false)
    private Integer quantity;
}

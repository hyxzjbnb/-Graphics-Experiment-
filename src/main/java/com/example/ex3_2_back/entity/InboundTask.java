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
@Table(name = "InboundTasks")
@Schema(description = "Inbound Tasks")
public class InboundTask {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "Inbound Task ID", example = "1")
    private Long inboundTaskId;

    @Column(nullable = false)
    @Schema(description = "Task Status", example = "Pending")
    private String status;

    @Column(nullable = false)
    @Schema(description = "Creation Timestamp", example = "2024-04-14T12:00:00")
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @Schema(description = "Last Updated Timestamp", example = "2024-04-14T12:00:00")
    private LocalDateTime updatedAt;
}

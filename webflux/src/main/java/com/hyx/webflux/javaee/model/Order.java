package com.hyx.webflux.javaee.model;

/**
 * @author hyxzjbnb
 * @create 2024-05-28-16:06
 */
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("orders") // 注意R2DBC通常小写表名
public class Order {
    @Id
    Integer id;

    private Long uid;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}

package com.example.ex3_2_back.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    Integer productId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = true, length = 255)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = true)
    private LocalDate productionDate;

    public Product(int i, String updatedProduct, String updatedDescription, int i1) {
    }

    public void setId(Integer productId) {
        this.productId = productId;
    }

    // 可以根据需要添加其他字段
}

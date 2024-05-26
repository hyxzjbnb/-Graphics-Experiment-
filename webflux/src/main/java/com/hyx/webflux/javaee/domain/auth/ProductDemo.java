package com.hyx.webflux.javaee.domain.auth;

import lombok.*;

/**
 * @author hyxzjbnb
 * @create 2024-05-26-20:23
 */
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ProductDemo {
    private String name;

    private String description;

    private float price;
}

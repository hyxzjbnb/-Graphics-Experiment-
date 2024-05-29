package com.hyx.webflux.javaee.domain.auth;

import lombok.*;

/**
 * @author hyxzjbnb
 * @create 2024-05-29-17:33
 */
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Order1 {
    private int pid;
    private int q;
    private int oid;
}

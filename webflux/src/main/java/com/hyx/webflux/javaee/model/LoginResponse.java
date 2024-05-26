package com.hyx.webflux.javaee.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hyxzjbnb
 * @create 2024-05-25-22:35
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {
    String username;
    String auth;
    String token;
}

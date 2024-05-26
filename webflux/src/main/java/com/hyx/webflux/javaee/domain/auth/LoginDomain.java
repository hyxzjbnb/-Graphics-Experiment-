package com.hyx.webflux.javaee.domain.auth;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class LoginDomain {

    private String username;

    private String password;
}

package com.hyx.webflux.javaee.domain.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class RegisterDomain {

    @Schema(defaultValue = "Test")
    private String username;

    @Schema(defaultValue = "z1111")
    private String password;

    @Schema(defaultValue = "z1111")
    private String authorities;


}

package com.example.ex3_2_back.domain.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class LoginDomain {
    @NotBlank
    @Schema(description = "username", defaultValue = "15", requiredMode = Schema.RequiredMode.AUTO)
    private String username;
    @NotBlank
    @Schema(description = "password", defaultValue = "15", requiredMode = Schema.RequiredMode.AUTO)
    private String password;
}

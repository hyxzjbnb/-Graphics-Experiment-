package com.example.ex3_2_back.domain.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class RegisterDomain {
    @NotBlank
    @Schema(defaultValue = "Test")
    private String username;
    @NotBlank
    @Schema(defaultValue = "z1111")
    private String password1;
    @NotBlank
    @Schema(defaultValue = "z1111")
    private String password2;
    @Email
    @Schema(defaultValue = "test@example.com")
    private String email;
    @Schema(defaultValue = "18810393672")
    private String phone;
}

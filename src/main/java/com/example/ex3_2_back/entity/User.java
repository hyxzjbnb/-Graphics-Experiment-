package com.example.ex3_2_back.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;


@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "t_User")
@Schema(description = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    @Column(unique = true, nullable = false)
    @Schema(defaultValue = "15")
    String name;
    @Schema(defaultValue = "15")
    String password;
    @Email
    @Schema(defaultValue = "2334579301@qq.com")
    String email;
    @Schema(defaultValue = "133")
    String phone;
    @Schema(defaultValue = "北京市海淀区北京交通大学")
    String address;
}

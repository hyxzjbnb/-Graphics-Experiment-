package com.hyx.webflux.javaee.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


/**
 * @author hyxzjbnb
 * @create 2024-05-25-22:35
 */

@Table("my_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyUser {

    @Id
    Long id;
    String username;
    String password;
    String authorities;

}

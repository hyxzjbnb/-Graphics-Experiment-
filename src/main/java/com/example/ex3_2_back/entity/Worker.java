package com.example.ex3_2_back.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    String name;
    String email;
    String phone;
    String address;
}

package com.example.ex3_2_back.controller;

import com.example.ex3_2_back.entity.User;
import com.example.ex3_2_back.repository.UserRepository;
import com.example.ex3_2_back.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper; // For JSON conversion

    @MockBean
    private AuthService authService;

    @BeforeEach
    void setUp() {
        User user1 = new User(1, "Alice", "password123", "alice@example.com");
        User user2 = new User(2, "Bob", "password456", "bob@example.com");

        given(userRepository.findAll()).willReturn(Arrays.asList(user1, user2));
        given(userRepository.findByName("Alice")).willReturn(Optional.of(user1));
    }

    @Test
    void getAllUsers_ShouldReturnAllUsers() throws Exception {
        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(2));
    }

    @Test
    void getUserByName_ShouldReturnUser() throws Exception {
        mockMvc.perform(get("/user/Alice"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Alice"));
    }

    @Test
    void createUser_ShouldCreateUser() throws Exception {
        User newUser = new User(null, "Charlie", "password789", "charlie@example.com");
        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteUser_ShouldDeleteUser() throws Exception {
        mockMvc.perform(delete("/user/Bob"))
                .andExpect(status().isOk());
    }
}

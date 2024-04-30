package com.example.ex3_2_back.controller;

import com.example.ex3_2_back.entity.InboundTask;
import com.example.ex3_2_back.interceptor.RateLimitInterceptor;
import com.example.ex3_2_back.service.AuthService;
import com.example.ex3_2_back.service.AdminAuthService;
import com.example.ex3_2_back.service.InboundTaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InboundTaskController.class)
public class InboundTaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InboundTaskService inboundTaskService;

    @Autowired
    private ObjectMapper objectMapper;

    private InboundTask inboundTask;

    @MockBean
    private AuthService authService;

    @MockBean
    private AdminAuthService adminauthService;

    @MockBean
    private RateLimitInterceptor rateLimitInterceptor;

    @BeforeEach
    void setUp() {
        inboundTask = new InboundTask();
        inboundTask.setId(1);
        inboundTask.setStatus("Pending");
    }

    @Test
    void testCreateInboundTask() throws Exception {
        when(inboundTaskService.createInboundTask()).thenReturn(inboundTask);

        mockMvc.perform(post("/inbound-tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.status").value("Pending"));
    }

    @Test
    void testDeleteInboundTask() throws Exception {
        mockMvc.perform(delete("/inbound-tasks/1"))
                .andExpect(status().isOk());

        verify(inboundTaskService, times(1)).deleteTask(1);
        verifyNoMoreInteractions(inboundTaskService);
    }
}

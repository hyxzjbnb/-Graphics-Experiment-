package com.example.ex3_2_back.controller;

/**
 * @author hyxzjbnb
 * @create 2024-04-29-19:58
 */
import com.example.ex3_2_back.controller.OutboundTaskController;
import com.example.ex3_2_back.entity.OutboundTask;
import com.example.ex3_2_back.domain.Result;
import com.example.ex3_2_back.service.AdminAuthService;
import com.example.ex3_2_back.service.AuthService;
import com.example.ex3_2_back.service.OutboundTaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.mockito.Mockito.*;
@WebMvcTest(OutboundTaskController.class)
public class OutboundTaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OutboundTaskService outboundTaskService;

    private OutboundTask outboundTask;

    @MockBean
    private AuthService authService;

    @MockBean
    private AdminAuthService adminauthService;

    @BeforeEach
    void setUp() {
        outboundTask = new OutboundTask();
        outboundTask.setId(1);
        outboundTask.setStatus("Pending");
    }

    @Test
    void testCreateOutboundTask() throws Exception {
        when(outboundTaskService.createOutboundTask(anyInt())).thenReturn(outboundTask);

        mockMvc.perform(post("/outbound-tasks")
                        .param("orderId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.status").value("Pending"));
    }

    @Test
    void testUpdateTaskStatus() throws Exception {
        when(outboundTaskService.updateTaskStatus(eq(1), any(String.class))).thenReturn(outboundTask);

        mockMvc.perform(patch("/outbound-tasks/1")
                        .param("status", "Completed")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.status").value("Pending"));
    }

    @Test
    void testGetOutboundTasks() throws Exception {
        // Implement your mock response for getOutboundTasks if needed
        // when(outboundTaskService.getOutboundTasks(anyString(), anyString(), anyString())).thenReturn(List.of(outboundTask));

        mockMvc.perform(get("/outbound-tasks"))
                .andExpect(status().isOk())
                // Add your assertions for the response body here
                // .andExpect(jsonPath("$[0].status").value("Pending"));
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}


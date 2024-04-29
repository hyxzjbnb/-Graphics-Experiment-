package com.example.ex3_2_back.controller;

import com.example.ex3_2_back.entity.Order;
import com.example.ex3_2_back.service.AdminAuthService;
import com.example.ex3_2_back.service.AuthService;
import com.example.ex3_2_back.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.Mockito.*;


@WebMvcTest(OrderController.class)
public class OrderControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    private Order order;

    @MockBean
    private AuthService authService;

    @MockBean
    private AdminAuthService adminauthService;


    @BeforeEach
    void setUp() {
        order = new Order();
        order.setId(1);
        order.setStatus("Pending");
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void testCreateOrder() throws Exception {
        when(orderService.createOrder(any(Order.class))).thenReturn(order);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.status").value("Pending"));
    }

    @Test
    void testUpdateOrderStatus() throws Exception {
        when(orderService.updateOrderStatus(eq(1), any(String.class))).thenReturn(order);

        mockMvc.perform(patch("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\":\"Completed\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.status").value("Pending"));
    }


    @Test
    void testGetOrders() throws Exception {
        List<Order> orders = Arrays.asList(order);
        when(orderService.getOrders(null, null)).thenReturn(orders);

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].status").value("Pending"));
    }

    @Test
    public void testCancelOrder() throws Exception {
        doNothing().when(orderService).cancelOrder(anyInt());

        mockMvc.perform(delete("/orders/1"))
                .andExpect(status().isOk());

        verify(orderService).cancelOrder(anyInt());
    }


}


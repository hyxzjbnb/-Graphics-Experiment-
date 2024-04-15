package com.example.ex3_2_back.controller;

import com.example.ex3_2_back.entity.OrderItem;
import com.example.ex3_2_back.service.OrderItemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class OrderItemControllerTests {

    @Mock
    private OrderItemService orderItemService;

    @InjectMocks
    private OrderItemController orderItemController;

    private MockMvc mockMvc;

    @Test
    void testAddOrderItem() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(orderItemController).setSingleView(new MappingJackson2JsonView()).build();
        OrderItem orderItem = new OrderItem();
        when(orderItemService.addOrderItem(any(OrderItem.class))).thenReturn(orderItem);

        mockMvc.perform(post("/order-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productId\":1,\"orderId\":1,\"quantity\":10}")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void testUpdateOrderItem() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(orderItemController).setSingleView(new MappingJackson2JsonView()).build();
        OrderItem orderItem = new OrderItem();
        when(orderItemService.updateOrderItem(eq(1), eq(5))).thenReturn(orderItem);

        mockMvc.perform(patch("/order-items/1")
                        .param("newQuantity", "5")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void testRemoveOrderItem() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(orderItemController).setSingleView(new MappingJackson2JsonView()).build();
        doNothing().when(orderItemService).removeOrderItem(eq(1));

        mockMvc.perform(delete("/order-items/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }
}

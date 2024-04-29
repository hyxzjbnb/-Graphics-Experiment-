package com.example.ex3_2_back.controller;

import com.example.ex3_2_back.entity.Inventory;
import com.example.ex3_2_back.entity.Product;
import com.example.ex3_2_back.service.AdminAuthService;
import com.example.ex3_2_back.service.AuthService;
import com.example.ex3_2_back.service.InventoryService;
import com.example.ex3_2_back.domain.Result;
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

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InventoryController.class)
public class InventoryControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventoryService inventoryService;

    @Autowired
    private ObjectMapper objectMapper;

    private Inventory inventory;

    @MockBean
    private AuthService authService;

    @MockBean
    private AdminAuthService adminauthService;
    @BeforeEach
    void setUp() {
        // 创建一个产品对象
        Product product = new Product();
        product.setId(1);
        product.setName("Product1");

        // 创建一个库存对象，并关联产品
        inventory = new Inventory();
        inventory.setId(1);
        inventory.setQuantity(10);
        inventory.setProduct(product);
    }

    @Test
    void testUpdateInventory_Success() throws Exception {
        when(inventoryService.updateInventory(eq(1), anyInt())).thenReturn(inventory);

        mockMvc.perform(patch("/inventory/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("changeInQuantity", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.inventoryId").value(1))
                .andExpect(jsonPath("$.data.quantity").value(10))
                .andExpect(jsonPath("$.data.product.productId").value(1))
                .andExpect(jsonPath("$.data.product.name").value("Product1"));
    }

    @Test
    void testGetInventory_Success() throws Exception {
        when(inventoryService.getInventoryQuantity(eq(1))).thenReturn(10);

        mockMvc.perform(get("/inventory")
                        .param("productId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(10));
    }

    @Test
    void testGetLowInventoryWarnings() throws Exception {
        List<Inventory> lowInventoryItems = Arrays.asList(inventory);
        when(inventoryService.checkLowInventory()).thenReturn(lowInventoryItems);

        mockMvc.perform(get("/inventory/warnings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].inventoryId").value(1))
                .andExpect(jsonPath("$.data[0].quantity").value(10))
                .andExpect(jsonPath("$.data[0].product.productId").value(1))
                .andExpect(jsonPath("$.data[0].product.name").value("Product1"));
    }
}

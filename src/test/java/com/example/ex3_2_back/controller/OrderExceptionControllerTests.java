package com.example.ex3_2_back.controller;

import com.example.ex3_2_back.entity.OrderException;
import com.example.ex3_2_back.service.OrderExceptionService;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class OrderExceptionControllerTests {

    @Mock
    private OrderExceptionService orderExceptionService;

    @InjectMocks
    private OrderExceptionController orderExceptionController;

    private MockMvc mockMvc;

    @Test
    void testRecordException() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(orderExceptionController).setSingleView(new MappingJackson2JsonView()).build();
        OrderException exception = new OrderException();
        when(orderExceptionService.recordException(eq(1), any(String.class))).thenReturn(exception);

        mockMvc.perform(post("/order-exceptions")
                        .param("orderId", "1")
                        .param("description", "Test exception")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

}

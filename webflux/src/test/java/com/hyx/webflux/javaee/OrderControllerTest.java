package com.hyx.webflux.javaee;

/**
 * @author hyxzjbnb
 * @create 2024-05-28-16:25
 */
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyx.webflux.javaee.model.Order;
import com.hyx.webflux.javaee.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureWebTestClient
public class OrderControllerTest {

    @Autowired
    private WebTestClient webTestClient;
    @Test
    void testCreateOrder() {
        Order order = new Order();
        order.setUid(12L);
        order.setStatus("Pending");
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        System.out.println(order);
        webTestClient.post().uri("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(order)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo("200");
    }

    @Test
    void testUpdateOrderStatus() {
        Integer orderId = 1;
        String newStatus = "Processing";

        webTestClient.patch().uri(uriBuilder ->
                        uriBuilder.path("/orders/{orderId}")
                                .queryParam("status", newStatus)
                                .build(orderId))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo("200");
    }


    @Test
    void testCancelOrder() {
        Integer orderId = 2;

        webTestClient.delete().uri("/orders/{orderId}", orderId)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo("200");
    }

    @Test
    void testGetOrders() {
        webTestClient.get().uri(uriBuilder -> uriBuilder.path("/orders")
                        .queryParam("uid", 1)
                        .queryParam("status", "Pending")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo("200");
    }
}
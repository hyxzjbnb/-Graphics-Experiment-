package com.hyx.webflux.javaee;

/**
 * @author hyxzjbnb
 * @create 2024-05-28-16:25
 */
import com.hyx.webflux.javaee.model.HttpResult;
import com.hyx.webflux.javaee.model.Order;
import com.hyx.webflux.javaee.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureWebTestClient
public class OrderControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private OrderService orderService;

    private Order mockOrder;

    @BeforeEach
    public void setUp() {
        mockOrder = new Order();
        mockOrder.setId(1);
        mockOrder.setStatus("Pending");
    }

    @Test
    void testCreateOrder() {
        Mockito.when(orderService.createOrder(any(Order.class))).thenReturn(Mono.just(mockOrder));

        webTestClient.post().uri("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(mockOrder), Order.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(200);
    }

    @Test
    void testUpdateOrderStatus() {
        Mockito.when(orderService.updateOrderStatus(any(Order.class))).thenReturn(Mono.just(mockOrder));

        webTestClient.patch().uri("/orders/1?status=Completed")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(200);
    }


    @Test
    void testGetOrders() {
        Mockito.when(orderService.getOrders(any(Order.class))).thenReturn(Flux.just(mockOrder));

        webTestClient.get().uri(uriBuilder ->
                        uriBuilder.path("/orders")
                                .queryParam("id", 1)
                                .queryParam("status", "Pending")
                                .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(200)
                .jsonPath("$.data[0].id").isEqualTo(1)
                .jsonPath("$.data[0].status").isEqualTo("Pending");
    }
}

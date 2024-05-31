package com.hyx.webflux.javaee;

/**
 * @author hyxzjbnb
 * @create 2024-05-29-17:39
 */
import com.hyx.webflux.javaee.model.HttpResult;
import com.hyx.webflux.javaee.model.OrderItem;
import com.hyx.webflux.javaee.domain.auth.Order1;
import com.hyx.webflux.javaee.service.OrderItemService;
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

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureWebTestClient
public class OrderItemControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private OrderItemService orderItemService;

    private OrderItem mockOrderItem;

    @BeforeEach
    public void setUp() {
        mockOrderItem = new OrderItem();
        mockOrderItem.setOid(1);
        mockOrderItem.setPid(101);
        mockOrderItem.setQuantity(5);
    }

    @Test
    void testAddOrderItem() {
        Mockito.when(orderItemService.createOrder(any(OrderItem.class))).thenReturn(Mono.just(mockOrderItem));

        Order1 order1 = new Order1();
        order1.setOid(1);
        order1.setPid(101);
        order1.setQ(5);

        webTestClient.post().uri("/order-items")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(order1), Order1.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(200);
    }

    @Test
    void testUpdateOrderItem() {
        Mockito.when(orderItemService.updateOrderItem(any(Integer.class), any(Integer.class))).thenReturn(Mono.just(mockOrderItem));

        webTestClient.patch().uri("/order-items/1?newQuantity=10")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(200);
    }

    @Test
    void testRemoveOrderItem() {
        Mockito.when(orderItemService.removeOrderItem(1)).thenReturn(Mono.empty());

        webTestClient.delete().uri("/order-items/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(200);
    }

    @Test
    void testGetOrderItem() {
        Mockito.when(orderItemService.getOrderItem(1)).thenReturn(Mono.just(mockOrderItem));

        webTestClient.get().uri("/order-items/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(200)
                .jsonPath("$.data.oid").isEqualTo(1)
                .jsonPath("$.data.pid").isEqualTo(101)
                .jsonPath("$.data.quantity").isEqualTo(5);
    }
}
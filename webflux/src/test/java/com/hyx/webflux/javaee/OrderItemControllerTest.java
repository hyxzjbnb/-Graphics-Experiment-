package com.hyx.webflux.javaee;

/**
 * @author hyxzjbnb
 * @create 2024-05-29-17:39
 */
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyx.webflux.javaee.controller.InventoryController;
import com.hyx.webflux.javaee.domain.auth.InventoryDemo;
import com.hyx.webflux.javaee.domain.auth.Order1;
import com.hyx.webflux.javaee.model.HttpResult;
import com.hyx.webflux.javaee.model.Inventory;
import com.hyx.webflux.javaee.model.Order;
import com.hyx.webflux.javaee.model.OrderItem;
import com.hyx.webflux.javaee.service.InventoryService;
import com.hyx.webflux.javaee.service.OrderItemService;
import com.hyx.webflux.javaee.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static reactor.core.publisher.Mono.when;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureWebTestClient
public class OrderItemControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private OrderItemService orderItemService;
    //测试保存订单单

    @Test
    void testCreateOrderItem() {
        Order1 Order = new Order1();
        Order.setQ(50);
        Order.setPid(101);
        Order.setOid(11);

        webTestClient.post()
                .uri("/order-items")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(Order), InventoryDemo.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo("200");
    }

    @Test
    public void testUpdateOrderItem() {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(1);
        orderItem.setQuantity(10);

        //when(orderItemService.updateOrderItem(1, 10)).thenReturn(Mono.just(orderItem));

        webTestClient.patch()
                .uri("/order-items/1?newQuantity=10")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo("200");

    }

    @Test
    public void testRemoveOrderItem() {
        when(orderItemService.removeOrderItem(1)).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/order-items/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo("200");

    }

    @Test
    public void testGetOrderItem() {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(1);
        orderItem.setQuantity(10);

        when(orderItemService.getOrderItem(1)).thenReturn(Mono.just(orderItem));

        webTestClient.get()
                .uri("/order-items/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo("200");
    }
}

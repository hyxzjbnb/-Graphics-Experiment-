package com.hyx.webflux.javaee;

/**
 * @author hyxzjbnb
 * @create 2024-05-29-17:39
 */
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyx.webflux.javaee.controller.InventoryController;
import com.hyx.webflux.javaee.domain.auth.InventoryDemo;
import com.hyx.webflux.javaee.model.HttpResult;
import com.hyx.webflux.javaee.model.Inventory;
import com.hyx.webflux.javaee.model.Order;
import com.hyx.webflux.javaee.service.InventoryService;
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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static reactor.core.publisher.Mono.when;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureWebTestClient
public class InventoryControllerTest {
    @Autowired
    private WebTestClient webTestClient;
    //测试保存库存
    @Test
    void testCreateInventory() {
        InventoryDemo inventoryDemo = new InventoryDemo();
        inventoryDemo.setQ(50);
        inventoryDemo.setPid(101);

        webTestClient.post()
                .uri("/inventory")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(inventoryDemo), InventoryDemo.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo("200");
    }
    //测试更新
    @Test
    public void testUpdateInventory() {
        webTestClient.patch()
                .uri("/inventory/2?changeInQuantity=10")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo("200");

    }

    //测试查询
    @Test
    public void testGetInventory() {

        webTestClient.get()
                .uri("/inventory?productId=101")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$..code").isEqualTo(200);

    }

    @Test
    public void testDeleteInventory() {

        webTestClient.delete()
                .uri("/inventory?productId=2")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo("200");

    }
}

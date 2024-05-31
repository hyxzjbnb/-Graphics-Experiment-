package com.hyx.webflux.javaee;

/**
 * @author hyxzjbnb
 * @create 2024-05-29-17:39
 */
import com.hyx.webflux.javaee.controller.InventoryController;
import com.hyx.webflux.javaee.domain.auth.InventoryDemo;
import com.hyx.webflux.javaee.model.HttpResult;
import com.hyx.webflux.javaee.model.Inventory;
import com.hyx.webflux.javaee.service.InventoryService;
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

import java.time.LocalDateTime;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureWebTestClient
public class InventoryControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private InventoryService inventoryService;

    private InventoryDemo mockInventoryDemo;
    private Inventory mockInventory;

    @BeforeEach
    public void setUp() {
        mockInventoryDemo = new InventoryDemo();
        mockInventoryDemo.setQ(50);
        mockInventoryDemo.setPid(101);

        mockInventory = new Inventory();
        mockInventory.setQuantity(50);
        mockInventory.setPid(101);
        mockInventory.setDate(LocalDateTime.now());
    }

    @Test
    void testCreateInventory() {
        Mockito.when(inventoryService.createInventory(any(Inventory.class))).thenReturn(Mono.just(mockInventory));

        webTestClient.post().uri("/inventory")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(mockInventoryDemo), InventoryDemo.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(200);
    }

    @Test
    void testUpdateInventory() {
        Mockito.when(inventoryService.updateInventory(any(Inventory.class))).thenReturn(Mono.just(mockInventory));

        webTestClient.patch().uri("/inventory/101?changeInQuantity=10")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(200);
    }

    @Test
    void testGetInventory() {
        //Mockito.when(inventoryService.getInventoryQuantity(101)).thenReturn(Mono.just(50));

        webTestClient.get().uri("/inventory?productId=101")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(200)
                .jsonPath("$.data").isEqualTo(50);
    }

    @Test
    void testDeleteInventory() {
        Mockito.when(inventoryService.deleteInventory(101)).thenReturn(Mono.empty());

        webTestClient.delete().uri("/inventory?productId=101")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(200);
    }
}
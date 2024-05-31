package com.hyx.webflux.javaee;

import com.hyx.webflux.javaee.model.OutboundTask;
import com.hyx.webflux.javaee.model.Order;
import com.hyx.webflux.javaee.repository.OutboundTaskRepository;
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

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureWebTestClient
public class OutboundTaskControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private OutboundTaskRepository outboundTaskRepository;

    private OutboundTask mockOutboundTask;

    @BeforeEach
    public void setUp() {
        Order order = new Order(); // Create an Order object with orderId 1
        mockOutboundTask = OutboundTask.builder()
                .outboundTaskId(1)
                .order_id(order.getId())
                .status("Pending")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    void testUpdateTaskStatus() {
        Mockito.when(outboundTaskRepository.findById(1)).thenReturn(Mono.just(mockOutboundTask));
        Mockito.when(outboundTaskRepository.save(Mockito.any(OutboundTask.class))).thenReturn(Mono.just(mockOutboundTask));

        webTestClient.patch().uri("/outbound-tasks/{taskId}?status=Completed", 1)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.success").isEqualTo(true)
                .jsonPath("$.data.status").isEqualTo("Completed");
    }
}

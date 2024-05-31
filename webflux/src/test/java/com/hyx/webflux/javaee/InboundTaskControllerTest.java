package com.hyx.webflux.javaee;

import com.hyx.webflux.javaee.model.InboundTask;
import com.hyx.webflux.javaee.service.InboundTaskService;
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
public class InboundTaskControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private InboundTaskService inboundTaskService;

    private InboundTask mockInboundTask;

    @BeforeEach
    public void setUp() {
        mockInboundTask = InboundTask.builder()
                .id(1)
                .status("Pending")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    void testCreateInboundTask() {
        Mockito.when(inboundTaskService.createInboundTask()).thenReturn(Mono.just(mockInboundTask));

        webTestClient.post().uri("/inbound-tasks")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.success").isEqualTo(true)
                .jsonPath("$.data.inboundTaskId").isEqualTo(1)
                .jsonPath("$.data.status").isEqualTo("Pending");
    }

    @Test
    void testUpdateTaskStatus() {
        Mockito.when(inboundTaskService.updateTaskStatus(1, "Completed")).thenReturn(Mono.just(mockInboundTask));

        webTestClient.patch().uri("/inbound-tasks/{taskId}?status=Completed", 1)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.success").isEqualTo(true)
                .jsonPath("$.data.status").isEqualTo("Completed");
    }

    @Test
    void testDeleteTask() {
        Mockito.when(inboundTaskService.deleteTask(1)).thenReturn(Mono.empty());

        webTestClient.delete().uri("/inbound-tasks/{taskId}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.success").isEqualTo(true)
                .jsonPath("$.message").isEqualTo("Inbound task deleted successfully.");
    }
}

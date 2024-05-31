package com.hyx.webflux.javaee;

/**
 * @author hyxzjbnb
 * @create 2024-05-29-17:48
 */
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.server.WebSession;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SessionControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testGetSession() {
        webTestClient.get().uri("/session")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.attributes").isEmpty()
                .jsonPath("$.creationTime").isNumber()
                .jsonPath("$.lastAccessTime").isNumber()
                .jsonPath("$.maxIdleTime").isEqualTo(1800)
                .jsonPath("$.state").isEqualTo("NEW")
                .jsonPath("$.started").isEqualTo(false)
                .jsonPath("$.expired").isEqualTo(false);
    }

    @Test
    public void testCreateSession() {
        webTestClient.get().uri("/session/create")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .consumeWith(response -> {
                    String responseBody = response.getResponseBody();
                    assertThat(responseBody).contains("create session => ");
                });
    }

    @Test
    public void testInvalidateSession() {
        webTestClient.get().uri("/session/invalidate")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .consumeWith(response -> {
                    String responseBody = response.getResponseBody();
                    assertThat(responseBody).contains("invalidate session => ");
                });
    }
}

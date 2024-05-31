package com.hyx.webflux.javaee;

/**
 * @author hyxzjbnb
 * @create 2024-05-31-21:54
 */
import com.hyx.webflux.javaee.model.MyUser;
import com.hyx.webflux.javaee.service.MyUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureWebTestClient
public class MyUserControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private MyUserService myUserService;

    private MyUser mockUser;

    @BeforeEach
    public void setUp() {
        mockUser = new MyUser();
        mockUser.setId(1L);
        mockUser.setUsername("testuser");
        mockUser.setPassword("password");
    }

    @Test
    void testFindAll() {
        Mockito.when(myUserService.findAll()).thenReturn(Flux.just(mockUser));

        webTestClient.get().uri("/user/findAll")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].username").isEqualTo("testuser");
    }

    @Test
    void testFindByUsername() {
        Mockito.when(myUserService.findByUsername("testuser")).thenReturn(Mono.just(mockUser));

        webTestClient.get().uri(uriBuilder ->
                        uriBuilder.path("/user")
                                .queryParam("username", "testuser")
                                .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.username").isEqualTo("testuser");
    }

    @Test
    void testSave() {
        Mockito.when(myUserService.save(any(MyUser.class))).thenReturn(Mono.just(mockUser));

        webTestClient.post().uri("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(mockUser), MyUser.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.username").isEqualTo("testuser");
    }

    @Test
    void testDelete() {
        Mockito.when(myUserService.deleteById(1L)).thenReturn(Mono.empty());

        webTestClient.delete().uri(uriBuilder ->
                        uriBuilder.path("/user")
                                .queryParam("id", "1")
                                .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testFindAllAsSlice() {
        Mockito.when(myUserService.findAllAsSlice(any(), any(Pageable.class)))
                .thenReturn(Flux.just(mockUser));

        webTestClient.get().uri("/user/slice")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].username").isEqualTo("testuser");
    }

    @Test
    void testFindAllAsPage() {
        Page<MyUser> page = new PageImpl<>(Collections.singletonList(mockUser), PageRequest.of(0, 5), 1);
        Mockito.when(myUserService.findAllAsPage(any(), any(Pageable.class))).thenReturn(Mono.just(page));

        webTestClient.get().uri("/user/page")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.content[0].username").isEqualTo("testuser");
    }
}

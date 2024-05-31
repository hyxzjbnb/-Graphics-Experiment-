package com.hyx.webflux.javaee;

/**
 * @author hyxzjbnb
 * @create 2024-05-31-21:57
 */
import com.hyx.webflux.javaee.domain.auth.ProductDemo;
import com.hyx.webflux.javaee.domain.auth.ProductDemo1;
import com.hyx.webflux.javaee.model.HttpResult;
import com.hyx.webflux.javaee.model.Product;
import com.hyx.webflux.javaee.service.ProductCaching;
import com.hyx.webflux.javaee.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.mockito.ArgumentMatchers.any;


@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureWebTestClient
public class ProductControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductCaching productCaching;

    private Product mockProduct;
    private ProductDemo mockProductDemo;
    private ProductDemo1 mockProductDemo1;

    @BeforeEach
    public void setUp() {
        float a = 100;
        int b = 1;
        mockProduct = new Product();
        mockProduct.setName("Test Product");
        mockProduct.setDescription("Test Description");
        mockProduct.setPrice(a);
        mockProduct.setDate(LocalDate.now());

        mockProductDemo = new ProductDemo();
        mockProductDemo.setName("Test Product");
        mockProductDemo.setDescription("Test Description");
        mockProductDemo.setPrice(a);

        mockProductDemo1 = new ProductDemo1();
        mockProductDemo1.setName("Test Product");
        mockProductDemo1.setId(b);
    }

    @Test
    void testAddProduct() {
        Mockito.when(productService.saveProduct(any(Product.class))).thenReturn(Mono.just(mockProduct));

        webTestClient.post().uri("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(mockProductDemo), ProductDemo.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(200);
    }

    @Test
    void testUpdateProductCaching() {
        Mockito.when(productCaching.updateProduct(any(Product.class))).thenReturn(Mono.just(mockProduct));

        webTestClient.patch().uri("/products/caching")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(mockProductDemo), ProductDemo.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(200);
    }

    @Test
    void testAddProductCaching() {
        Mockito.when(productCaching.saveProduct(any(Product.class))).thenReturn(Mono.just(mockProduct));

        webTestClient.post().uri("/products/caching")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(mockProductDemo), ProductDemo.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(200);
    }

    @Test
    void testDeleteProductCaching() {
        //Mockito.doNothing().when(productCaching).deleteProductById(any(Product.class));

        webTestClient.method(HttpMethod.DELETE).uri("/products/caching")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(mockProductDemo1), ProductDemo1.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(200);
    }

    @Test
    void testDeleteProduct() {
        //Mockito.doNothing().when(productCaching).deleteProductById2(any(Product.class));

        webTestClient.method(HttpMethod.DELETE).uri("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(mockProductDemo1), ProductDemo1.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(200);
    }

    @Test
    void testGetProductCaching() {
        Mockito.when(productCaching.getProductById(any(Product.class))).thenReturn(Mono.just(mockProduct));

        webTestClient.method(HttpMethod.GET).uri("/products/caching")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(mockProductDemo1), ProductDemo1.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(200)
                .jsonPath("$.data.name").isEqualTo("Test Product");
    }
}
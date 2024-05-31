package com.hyx.webflux.javaee.service;

import com.hyx.webflux.javaee.model.OrderException;
import com.hyx.webflux.javaee.repository.OrderExceptionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderExceptionServiceTest {

    @Mock
    private OrderExceptionRepository orderExceptionRepository;

    @InjectMocks
    private OrderExceptionService orderExceptionService;

    @Test
    public void testCreateOrderException() {
        Integer orderId = 123;
        String description = "Test description";

        OrderException mockOrderException = OrderException.builder()
                .orderId(orderId)
                .description(description)
                .createdAt(LocalDateTime.now())
                .build();

        when(orderExceptionRepository.save(any(OrderException.class))).thenReturn(Mono.just(mockOrderException));

        Mono<OrderException> result = orderExceptionService.createOrderException(orderId, description);

        StepVerifier.create(result)
                .expectNextMatches(orderException -> orderException.getOrderId().equals(orderId) &&
                        orderException.getDescription().equals(description))
                .verifyComplete();
    }
}

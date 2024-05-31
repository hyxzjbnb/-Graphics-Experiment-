package com.hyx.webflux.javaee.repository;

import com.hyx.webflux.javaee.model.OrderException;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderExceptionRepository extends ReactiveCrudRepository<OrderException, Integer> {
}

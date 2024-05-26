package com.hyx.webflux.javaee.repository;

import com.hyx.webflux.javaee.model.MyUserDetails;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

/**
 * @author hyxzjbnb
 * @create 2024-05-25-22:35
 */
public interface MyUserDetailsRepository extends ReactiveCrudRepository<MyUserDetails, Long> {

    Mono<MyUserDetails> findByUsername (String username);

}

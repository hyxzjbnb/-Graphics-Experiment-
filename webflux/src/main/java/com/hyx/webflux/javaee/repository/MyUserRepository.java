package com.hyx.webflux.javaee.repository;

import com.hyx.webflux.javaee.model.MyUser;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

/**
 * @author hyxzjbnb
 * @create 2024-05-25-22:35
 */
public interface MyUserRepository extends ReactiveCrudRepository<MyUser, Long> {
    Mono<Long> count();
    Mono<MyUser> findByUsername(String username);

}

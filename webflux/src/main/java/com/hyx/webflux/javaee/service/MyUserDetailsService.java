package com.hyx.webflux.javaee.service;

import com.hyx.webflux.javaee.repository.MyUserDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @author hyxzjbnb
 * @create 2024-05-25-22:35
 */
@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements ReactiveUserDetailsService {

    private final MyUserDetailsRepository myUserDetailsRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {

        return myUserDetailsRepository.findByUsername(username)
                .cast(UserDetails.class);
    }
}

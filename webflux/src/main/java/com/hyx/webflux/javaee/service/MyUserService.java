package com.hyx.webflux.javaee.service;

import com.hyx.webflux.javaee.model.MyUser;
import com.hyx.webflux.javaee.repository.MyUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;

/**
 * @author hyxzjbnb
 * @create 2024-05-25-22:35
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class MyUserService {

    //private final PasswordEncoder password = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    private final MyUserRepository myUserRepository;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    public Flux<MyUser> findAll () {
        return myUserRepository.findAll();
    }

    public Mono<MyUser> findByUsername (String username) {
        return myUserRepository.findByUsername(username);
    }

    public Mono<MyUser> save(MyUser user) {
        return myUserRepository.save(user)
                .doOnSuccess(savedUser -> log.info("保存成功: " + savedUser))
                .doOnError(error -> log.info("保存失败: " + error.getMessage()));
    }

    public Mono<Void> deleteById (Long id) {
        return myUserRepository.deleteById(id);
    }


    public Flux<MyUser> findAllAsSlice(final Query query, final Pageable pageable) {
        final Query q = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .sort(pageable.getSort());
        return r2dbcEntityTemplate.select(q, MyUser.class);
    }


    public Mono<Page<MyUser>> findAllAsPage(final Query query, final Pageable pageable) {
        final Mono<Long> count = r2dbcEntityTemplate.count(query, MyUser.class);
        final Flux<MyUser> slice = findAllAsSlice(query, pageable);
        return Mono.zip(count, slice.buffer().next().defaultIfEmpty(Collections.emptyList()))
                .map(output -> new PageImpl<>(
                        output.getT2(),
                        pageable,
                        output.getT1()
                ));
    }
}

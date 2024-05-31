/*
 * Copyright 2019-Current jittagornp.me
 */
package com.hyx.webflux.javaee.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("session")
public class SessionController {

    @GetMapping
    public Mono<WebSession> getSession(final WebSession webSession) {
        return Mono.just(webSession);
    }

    @GetMapping("create")
    public Mono<String> createSession(final WebSession webSession) {
        webSession.start();
        return Mono.just("create session => " + webSession.getId());
    }

    @GetMapping("invalidate")
    public Mono<String> invalidateSession(final WebSession webSession) {
        return webSession.invalidate()
                .then(Mono.just("invalidate session => " + webSession.getId()));
    }
}

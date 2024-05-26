/*
 * Copyright 2019-Current jittagornp.me
 */
package com.hyx.webflux.javaee.config;

import io.r2dbc.spi.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;

/**
 * @author hyxzjbnb
 * @create 2024-05-25-22:35
 */
@Slf4j
@Configuration
public class R2dbcConfig {

    @Bean
    public R2dbcEntityTemplate r2dbcEntityTemplate(DatabaseClient connectionFactory) {
        return new R2dbcEntityTemplate(connectionFactory);
    }

}

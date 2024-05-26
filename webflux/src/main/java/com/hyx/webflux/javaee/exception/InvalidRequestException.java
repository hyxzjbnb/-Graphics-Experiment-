/*
 * Copyright 2019-Current jittagornp.me
 */
package com.hyx.webflux.javaee.exception;

/**
 * @author hyxzjbnb
 * @create 2024-05-25-22:35
 */
public class InvalidRequestException extends RuntimeException {

    public InvalidRequestException() {
    }

    public InvalidRequestException(String message) {
        super(message);
    }
}

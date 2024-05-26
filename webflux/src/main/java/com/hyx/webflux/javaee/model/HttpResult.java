package com.hyx.webflux.javaee.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hyxzjbnb
 * @create 2024-05-25-22:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpResult {

    private int code = 200;
    private String msg;
    private Object data;
}

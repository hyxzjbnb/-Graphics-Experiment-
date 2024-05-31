package com.hyx.webflux.javaee.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hyxzjbnb
 * @create 2024-05-25-22:35
 */
@Data
public class HttpResult {

    private int code = 200;
    private String msg;
    private Object data;

    public HttpResult() {
    }

    public HttpResult(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static HttpResult success(String msg, Object data) {
        return new HttpResult(200, msg, data);
    }

    public static HttpResult error(String msg, Object data) {
        return new HttpResult(500, msg, data);
    }

    // Getters and setters

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}


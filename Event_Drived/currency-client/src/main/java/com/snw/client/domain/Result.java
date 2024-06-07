package com.snw.client.domain;

/**
 * @author hyxzjbnb
 * @create 2024-06-07-23:04
 */
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Result {
    private boolean success;
    private String message;
    private Object data;
    private final List<Object> errors = new ArrayList<>();
    private final List<Object> devMessages = new ArrayList<>();

    public static Result success() {
        return Result.builder().success(true).build();
    }

    public static Result success(Object data) {
        return Result.builder().success(true).data(data).build();
    }

    public static Result error(String message) {
        return Result.builder().success(false).message(message).build();
    }

    public Result addErrors(Object error) {
        this.errors.add(error);
        return this;
    }

    public Result addDevMessages(Object devMessage) {
        this.devMessages.add(devMessage);
        return this;
    }

}


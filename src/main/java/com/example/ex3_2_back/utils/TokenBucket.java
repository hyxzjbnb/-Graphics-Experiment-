package com.example.ex3_2_back.utils;

/**
 * @author hyxzjbnb
 * @create 2024-04-29-21:40
 */
import java.util.concurrent.atomic.AtomicInteger;

public class TokenBucket {

    private final int capacity; // 令牌桶容量
    private final AtomicInteger tokens; // 当前令牌数量

    public TokenBucket(int capacity) {
        this.capacity = capacity;
        this.tokens = new AtomicInteger(capacity);
    }

    public boolean allowRequest() {
        while (true) {
            int currentTokens = tokens.get();
            if (currentTokens == 0) {
                return false; // 令牌桶中没有可用的令牌
            }
            if (tokens.compareAndSet(currentTokens, currentTokens - 1)) {
                return true; // 消耗一个令牌
            }
        }
    }

    public void refill() {
        tokens.set(capacity); // 重新填满令牌桶
    }
}

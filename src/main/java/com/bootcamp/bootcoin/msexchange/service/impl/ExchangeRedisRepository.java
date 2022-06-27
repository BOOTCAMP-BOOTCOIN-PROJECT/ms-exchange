package com.bootcamp.bootcoin.msexchange.repository;

import com.bootcamp.bootcoin.msexchange.entity.Exchange;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Map;

@Repository
@AllArgsConstructor
public class ExchangeRedisRepository implements RedisRepository{

    private static final String KEY = "exchange";
    private RedisTemplate<String, Exchange> redisTemplate;
    private HashOperations hashOperations;

    @PostConstruct
    private void init() {
        hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public Map<String, Exchange> getAll() {
        return hashOperations.entries(KEY);
    }

    @Override
    public void save(Exchange exchange) {
        hashOperations.put(KEY, exchange.getTag(), exchange);
    }

    @Override
    public Exchange findByTag(String tag) {
        return (Exchange) hashOperations.get(KEY, tag);
    }
}

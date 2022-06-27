package com.bootcamp.bootcoin.msexchange.service.impl;

import com.bootcamp.bootcoin.msexchange.entity.Exchange;
import com.bootcamp.bootcoin.msexchange.service.RedisRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExchangeRedisRepository implements RedisRepository {

    private static final String KEY = "Exchange";
    private final RedisTemplate<String, Exchange> redisTemplate;
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

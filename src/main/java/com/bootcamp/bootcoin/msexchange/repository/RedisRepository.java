package com.bootcamp.bootcoin.msexchange.repository;

import com.bootcamp.bootcoin.msexchange.entity.Exchange;

import java.util.Map;

public interface RedisRepository {
    public Map<String, Exchange> getAll();
    public void save(Exchange exchange);
    public Exchange findByTag(String tag);
}

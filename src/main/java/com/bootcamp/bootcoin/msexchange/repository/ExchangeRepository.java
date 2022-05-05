package com.bootcamp.bootcoin.msexchange.repository;

import com.bootcamp.bootcoin.msexchange.entity.Exchange;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ExchangeRepository extends ReactiveMongoRepository<Exchange, String> {

    Mono<Exchange> findByInputCurrency(String inputCurrency);

    Mono<Exchange> findByOutputCurrency(String outputCurrency);

    Mono<Exchange> findByTag(String tag);

    //Mono<Boolean> existsByDocumentNumber(String documentNumber);

}
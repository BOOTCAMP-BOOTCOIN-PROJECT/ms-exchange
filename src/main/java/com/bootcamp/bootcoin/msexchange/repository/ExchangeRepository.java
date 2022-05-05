package com.bootcamp.bootcoin.msexchange.repository;

import com.bootcamp.bootcoin.msexchange.entity.Exchange;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ExchangeRepository extends ReactiveMongoRepository<Exchange, String> {

    Flux<Exchange> findByInputCurrency(String inputCurrency);

    Flux<Exchange> findByOutputCurrency(String outputCurrency);

    Flux<Exchange> findByTag(String tag);

    //Mono<Boolean> existsByDocumentNumber(String documentNumber);

}
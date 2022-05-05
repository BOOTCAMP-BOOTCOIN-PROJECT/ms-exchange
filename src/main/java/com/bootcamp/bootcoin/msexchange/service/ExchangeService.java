package com.bootcamp.bootcoin.msexchange.service;

import com.bootcamp.bootcoin.msexchange.dto.CreateExchangeDto;
import com.bootcamp.bootcoin.msexchange.entity.Exchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ExchangeService {

    public Flux<Exchange> findAll();

    public Mono<Exchange> findById(String id);

    public Mono<Exchange> findByInputCurrency(String documentNumber);

    public Mono<Exchange> findByOutputCurrency(String documentNumber);

    public Mono<Exchange> save(CreateExchangeDto o);

    /*
    public Mono<Exchange> addAccount(String document, CreatePersonalClientAccountDto o);

    public Mono<Exchange> updateAccount(String document, UpdatePersonalClientAccountDto o);

    public Mono<Exchange> deleteAccount(String document, String accountId);

    public Mono<Exchange> update(UpdatePersonalClientDto o);

    public Mono<Exchange> delete(DeletePersonalClientDto o);*/

}
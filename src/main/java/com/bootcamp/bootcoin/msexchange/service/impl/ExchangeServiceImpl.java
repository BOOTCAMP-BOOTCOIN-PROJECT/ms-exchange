package com.bootcamp.bootcoin.msexchange.service.impl;

import com.bootcamp.bootcoin.msexchange.dto.CreateExchangeDto;
import com.bootcamp.bootcoin.msexchange.entity.Exchange;
import com.bootcamp.bootcoin.msexchange.repository.ExchangeRepository;
import com.bootcamp.bootcoin.msexchange.service.ExchangeService;
import com.bootcamp.bootcoin.msexchange.util.Util;
import com.bootcamp.bootcoin.msexchange.util.mapper.ExchangeModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ExchangeServiceImpl implements ExchangeService {

    private static ExchangeModelMapper modelMapper = ExchangeModelMapper.singleInstance();
    @Autowired
    private ReactiveMongoTemplate mongoTemplate;

    public final ExchangeRepository repository;

    //public final WebClientService webClient;

    @Override
    public Flux<Exchange> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Exchange> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Flux<Exchange> findByInputCurrency(String inputCurrency) {
        return repository.findByInputCurrency(inputCurrency);
    }

    @Override
    public Flux<Exchange> findByOutputCurrency(String outputCurrency) {
        return repository.findByOutputCurrency(outputCurrency);
    }

    @Override
    public Flux<Exchange> findByTag(String tag) {
        Query q = new Query();

        q.addCriteria(Criteria.where("tag").is(tag))
                .with(Sort.by(Sort.Direction.DESC, "emisionDate"))
                .limit(1);

        return mongoTemplate.find(q, Exchange.class);
    }

    @Override
    public Mono<Exchange> save(CreateExchangeDto o) {

        Util.verifyCurrency(o.getInputCurrency(), getClass());
        Util.verifyCurrency(o.getOutputCurrency(), getClass());

        return modelMapper.reverseMapCreateExchange(o)
                .flatMap( exchange -> repository.save(exchange))
                .onErrorResume( e -> Mono.error(e))
                .cast(Exchange.class);

    }

    /*@Override
    public Mono<Exchange> findByInputCurrency(String inputCurrency) {
        return repository.findByInputCurrency(inputCurrency)
                .switchIfEmpty(Mono.error(new BadRequestException(
                        "ID",
                        "An error occurred while trying to get an item.",
                        "The input currency "+inputCurrency+" does not exists.",
                        getClass(),
                        "getByDocumentNumber.switchIfEmpty"
                )))
                .flatMap( client -> {

                    return Flux.fromIterable(client.getAccounts())
                            .flatMap( product -> {

                                return webClient
                                        .getWebClient("searching transactions for product with id "+product.getAccountId())
                                        .get()
                                        .uri("transactions/" + product.getAccountId() )
                                        .retrieve()
                                        .bodyToMono(BigDecimal.class)
                                        .switchIfEmpty(Mono.error(new NotFoundException(
                                                "ID",
                                                "Account with id "+ product.getAccountId() +" does not have transactions.",
                                                "An error occurred while trying to get personal client account information.",
                                                getClass(),
                                                "getByDocumentNumber.map.switchIfEmpty"
                                        )))
                                        .flatMap( balance -> {
                                            product.setBalance(balance);
                                            return Mono.just(product);
                                        })
                                        .thenReturn(product);
                            })
                            .then(Mono.just(client));
                } )
                .onErrorResume( e -> Mono.error(e))
                .cast(Exchange.class);

    }

    @Override
    public Mono<PersonalClient> addAccount(String documentNumber, CreatePersonalClientAccountDto o) {

        return repository.findByDocumentNumber(documentNumber)
                .switchIfEmpty(Mono.error(new Exception("An item with the document number " + documentNumber + " was not found. >> switchIfEmpty")))
                .flatMap( p -> {

                    Util.verifyCurrency(o.getAccountIsoCurrencyCode(), getClass());

                    return repository.save(modelMapper.reverseMapAddAccounts(p, o));
                } )
                .onErrorResume( e -> Mono.error(new BadRequestException(
                        "ID",
                        "An error occurred while trying to update an item.",
                        e.getMessage(),
                        getClass(),
                        "update.onErrorResume"
                )));
    }

    @Override
    public Mono<PersonalClient> updateAccount(String documentNumber, UpdatePersonalClientAccountDto o) {

        return repository.findByDocumentNumber(documentNumber)
                .switchIfEmpty(Mono.error(new Exception("An item with the document number " + documentNumber + " was not found. >> switchIfEmpty")))
                .flatMap( p -> {

                    Util.verifyCurrency(o.getAccountIsoCurrencyCode(), getClass());

                    return repository.save(modelMapper.reverseMapUpdateAccounts(p, o));
                } )
                .onErrorResume( e -> Mono.error(new BadRequestException(
                        "ID",
                        "An error occurred while trying to update an item.",
                        e.getMessage(),
                        getClass(),
                        "update.onErrorResume"
                )));
    }

    @Override
    public Mono<PersonalClient> deleteAccount(String documentNumber, String accountId) {

        return repository.findByDocumentNumber(documentNumber)
                .switchIfEmpty(Mono.error(new Exception("An item with the document number " + documentNumber + " was not found. >> switchIfEmpty")))
                .flatMap( p -> repository.save(modelMapper.reverseMapDeleteAccounts(p, accountId)) )
                .onErrorResume( e -> Mono.error(new BadRequestException(
                        "ID",
                        "An error occurred while trying to delete an item.",
                        e.getMessage(),
                        getClass(),
                        "delete.onErrorResume"
                )));
    }

    @Override
    public Mono<PersonalClient> update(UpdatePersonalClientDto o) {

        return repository.findByDocumentNumber(o.getDocumentNumber())
                .switchIfEmpty(Mono.error(new Exception("An item with the document number " + o.getDocumentNumber() + " was not found. >> switchIfEmpty")))
                .flatMap( p -> {

                    ClientProfiles.verifyPersonalProfiles(o.getProfile(), getClass(), "update.verifyProfile");

                    return repository.save(modelMapper.reverseMapUpdate(p, o));
                } )
                .onErrorResume( e -> Mono.error(new BadRequestException(
                        "ID",
                        "An error occurred while trying to update an item.",
                        e.getMessage(),
                        getClass(),
                        "update.onErrorResume"
                )));
    }

    @Override
    public Mono<PersonalClient> delete(DeletePersonalClientDto o) {

        return repository.findByDocumentNumber(o.getDocumentNumber())
                .switchIfEmpty(Mono.error(new Exception("An item with the document number " + o.getDocumentNumber() + " was not found. >> switchIfEmpty")))
                .flatMap( p -> repository.save(modelMapper.reverseMapDelete(p, o)))
                .onErrorResume( e -> Mono.error(new BadRequestException(
                        "ID",
                        "An error occurred while trying to delete an item.",
                        e.getMessage(),
                        getClass(),
                        "update.onErrorResume"
                )));
    }*/

}
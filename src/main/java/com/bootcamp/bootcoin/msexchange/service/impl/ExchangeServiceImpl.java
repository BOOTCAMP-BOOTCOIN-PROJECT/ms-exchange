package com.bootcamp.bootcoin.msexchange.service.impl;

import com.bootcamp.bootcoin.msexchange.dto.CreateExchangeDto;
import com.bootcamp.bootcoin.msexchange.entity.Exchange;
import com.bootcamp.bootcoin.msexchange.repository.ExchangeRepository;
import com.bootcamp.bootcoin.msexchange.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ExchangeServiceImpl implements ExchangeService {

    //static PersonalClientModelMapper modelMapper = PersonalClientModelMapper.singleInstance();

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
    public Mono<Exchange> findByInputCurrency(String inputCurrency) {
        return repository.findByInputCurrency(inputCurrency);
    }

    @Override
    public Mono<Exchange> findByOutputCurrency(String outputCurrency) {
        return repository.findByOutputCurrency(outputCurrency);
    }

    @Override
    public Mono<Exchange> save(CreateExchangeDto o) {

        return repository.findByTag(o.getInputCurrency()+"."+o.getOutputCurrency())
                .map( p -> {
                    /*throw new BadRequestException(
                            "DocumentNumber",
                            "[save] The document number "+o.getDocumentNumber()+ " is already in use.",
                            "An error occurred while trying to create an item.",
                            getClass(),
                            "save"
                    );*/
                    return null;
                } )
                .switchIfEmpty(Mono.defer(() -> {

                    /*Util.isNumber(o.getDocumentNumber(), "DocumentNumber", getClass(), "save");

                    Util.verifyDocumentNumber(
                            o.getDocumentType(),
                            o.getDocumentNumber(),
                            getClass(),
                            "save"
                    );

                    ClientProfiles.verifyPersonalProfiles(o.getProfile(), getClass(), "create.verifyProfile");

                    o.getAccounts().forEach( acc -> Util.verifyCurrency(acc.getAccountIsoCurrencyCode(), getClass()));

                    return repository.save(modelMapper.reverseMapCreateWithDate(o));

                     */
                    return null;

                }))
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
package com.bootcamp.bootcoin.msexchange.controller;

import com.bootcamp.bootcoin.msexchange.dto.CreateExchangeDto;
import com.bootcamp.bootcoin.msexchange.dto.GetExchangeDto;
import com.bootcamp.bootcoin.msexchange.entity.Exchange;
import com.bootcamp.bootcoin.msexchange.service.ExchangeService;
import com.bootcamp.bootcoin.msexchange.service.impl.ExchangeServiceImpl;
import com.google.gson.Gson;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("exchange")
@Tag(name = "Exchange Information", description = "Manage exchange information")
@CrossOrigin( value = { "*" })
@RequiredArgsConstructor
public class ExchangeController {

    public final ExchangeServiceImpl service;

    @GetMapping
    @Operation( summary = "List all currency exchange records", description = "")
    public Mono<ResponseEntity<Flux<Exchange>>> findAll() {
        return Mono.just(
                ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(service.findAll())
        );
    }

    @GetMapping("/{id}")
    @Operation( summary = "List a specific currency exchange record by id", description = "")
    public Mono<ResponseEntity<Mono<Exchange>>> findById(@PathVariable String id) {
        return Mono.just(
                ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(service.findById(id))
        );
    }

    @GetMapping("/input/{inputCurrency}")
    @Operation( summary = "List a currency exchange records by input currency", description = "")
    public Mono<ResponseEntity<Flux<Exchange>>> findByInputCurrency(@PathVariable String inputCurrency) {
        return Mono.just(
                ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(service.findByInputCurrency(inputCurrency))
        );
    }

    @GetMapping("/output/{outputCurrency}")
    @Operation( summary = "List a currency exchange records by output currency", description = "")
    public Mono<ResponseEntity<Flux<Exchange>>> findByOutputCurrency(@PathVariable String outputCurrency) {
        return Mono.just(
                ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(service.findByOutputCurrency(outputCurrency))
        );
    }

    @PostMapping("/tag")
    @Operation( summary = "List the last exchange rate by tag", description = "")
    public Mono<ResponseEntity<Flux<Exchange>>> findByTag(@RequestBody GetExchangeDto o) {
        String tag = o.getInputCurrency() + "." + o.getOutputCurrency();
        return Mono.just(
                ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(service.findByTag(tag))
        );
    }

    @PostMapping
    @Operation( summary = "Record a currency exchange (buying and selling)", description = "")
    public Mono<ResponseEntity<Exchange>> create(@RequestBody CreateExchangeDto o) {

        return service.save(o).map( p -> ResponseEntity
                .created(URI.create("/exchange/".concat(p.getId())))
                .contentType(MediaType.APPLICATION_JSON)
                .body(p)
        );
    }

    /*
    @PostMapping(value = "/{documentNumber}/accounts")
    public Mono<ResponseEntity<PersonalClient>> addAccounts(@PathVariable String documentNumber, @RequestBody CreatePersonalClientAccountDto o) {

        return service.addAccount(documentNumber, o)
                .map(p -> ResponseEntity.created(URI.create("/client/personal/"
                                .concat(documentNumber)
                        ))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(p))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/{documentNumber}/accounts")
    public Mono<ResponseEntity<PersonalClient>> updateAccounts(@PathVariable String documentNumber, @RequestBody UpdatePersonalClientAccountDto o) {

        return service.updateAccount(documentNumber, o)
                .map(p -> ResponseEntity.created(URI.create("/client/personal/"
                                .concat(documentNumber)
                        ))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(p))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/{documentNumber}/accounts/{accountId}")
    public Mono<ResponseEntity<PersonalClient>> deleteAccounts(@PathVariable String documentNumber, @PathVariable String accountId) {

        return service.deleteAccount(documentNumber, accountId)
                .map(p -> ResponseEntity.created(URI.create("/client/personal/"
                                .concat(documentNumber)
                        ))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(p))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping
    public Mono<ResponseEntity<PersonalClient>> update(@RequestBody UpdatePersonalClientDto o) {
        return service.update(o)
                .map(p -> ResponseEntity.created(URI.create("/client/personal/"
                                .concat(p.getId())
                        ))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(p))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public Mono<ResponseEntity<PersonalClient>> delete(@RequestBody DeletePersonalClientDto o) {
        return service.delete(o)
                .map(p -> ResponseEntity.created(URI.create("/client/personal/"
                                .concat(p.getId())
                        ))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(p))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

     */
}
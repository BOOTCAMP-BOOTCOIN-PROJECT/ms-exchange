package com.bootcamp.bootcoin.msexchange.util.mapper;

import com.bootcamp.bootcoin.msexchange.dto.CreateExchangeDto;
import com.bootcamp.bootcoin.msexchange.entity.Exchange;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Mono;

import java.util.Date;

public class ExchangeModelMapper {

    private final ModelMapper mapper = new ModelMapper();

    private static ExchangeModelMapper instance;

    private ExchangeModelMapper() { }

    public static ExchangeModelMapper singleInstance() {
        if ( instance == null ) {
            instance = new ExchangeModelMapper();
        }
        return instance;
    }

    //MAPPERS BEGIN
    public Mono<Exchange> reverseMapCreateExchange(CreateExchangeDto dto) {
        Exchange o = mapper.map(dto, Exchange.class);

        o.setEmisionDate(new Date());
        o.setTag(dto.getInputCurrency()+"."+dto.getOutputCurrency());

        return Mono.just(o);
    }

    /*
    public EnterpriseClient reverseMapAddAccounts(EnterpriseClient p, CreateEnterpriseClientAccountDto o) {

        GenericProduct acc = mapper.map(o, GenericProduct.class);

        acc.setAccountInsertionDate(new Date());
        acc.setAccountRegistrationStatus((short) 1);

        p.getAccounts().add(acc);

        return p;

    }

    public EnterpriseClient reverseMapUpdateAccounts(EnterpriseClient p, UpdateEnterpriseClientAccountDto o) {

        List<Integer> counter = new ArrayList<>();

        p.setAccounts(
                p.getAccounts().stream().map(a -> {
                            if( a.getAccountId().equals(o.getAccountId()) ) {
                                a.setAccountType(o.getAccountType());
                                a.setAccountUrl(o.getAccountUrl());
                                a.setAccountIsoCurrencyCode(o.getAccountIsoCurrencyCode());

                                counter.add(1);
                            }

                            return a;
                        })
                        .collect(Collectors.toList())
        );

        if(counter.isEmpty()) throw new BadRequestException(
                "ERROR",
                "The account with id " + o.getAccountId() + " does not exist",
                "An error occurred while trying to update an item.",
                getClass(),
                "update.onReverseMapping"
        );

        return p;

    }

    public EnterpriseClient reverseMapDeleteAccounts(EnterpriseClient p, String accountId) {

        List<Integer> counter = new ArrayList<>();

        p.setAccounts(
                p.getAccounts().stream().map(a -> {
                            if( a.getAccountId().equals(accountId) ) {
                                counter.add(1);
                                return null;
                            }
                            return a;
                        })
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList())
        );

        if(counter.isEmpty()) {
            throw new BadRequestException(
                    "ERROR",
                    "The account with id " + accountId + " does not exist",
                    "An error occurred while trying to delete an item.",
                    getClass(),
                    "delete.onReverseMapping"
            );
        }

        return p;

    }

    public EnterpriseClient reverseMapUpdate(EnterpriseClient p, UpdateEnterpriseClientDto updateDto) {

        p.setProfile(updateDto.getProfile());
        p.setAccounts(updateDto.getAccounts());
        p.setLegalResidence(updateDto.getLegalResidence());

        return p;
    }

    public EnterpriseClient reverseMapDelete(EnterpriseClient p, DeleteEnterpriseClientDto deleteDto) {

        p.setRegistrationStatus((short) 0);

        return p;
    }

     */

}

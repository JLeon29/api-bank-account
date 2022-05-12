package com.nttdata.account.services;

import com.nttdata.account.dto.Credit;
import com.nttdata.account.dto.CreditCard;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICreditService {
	
    Mono<Boolean> validateDebtorCredit(String customerIdentityNumber);
    Flux<Credit> getCredit(String customerIdentityNumber);
    Mono<CreditCard> getCreditcard(String customerIdentityNumber);
}


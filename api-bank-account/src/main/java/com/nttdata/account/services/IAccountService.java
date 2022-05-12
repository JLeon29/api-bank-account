package com.nttdata.account.services;

import com.nttdata.account.models.Account;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface IAccountService extends ICrudService<Account,String> {
	
	Flux<Account> findAllByCustomerIdentityNumber(String customerIdentityNumber);
    Mono<Account> findByCustomerIdentityNumber(String customerIdentityNumber);
    Mono<Account> validateCustomerIdentityNumber(String customerIdentityNumber);
    Mono<Account> findByAccountNumber(String accountNumber);

}

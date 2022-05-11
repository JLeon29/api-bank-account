package com.nttdata.account.service;

import com.nttdata.account.model.Account;
import com.nttdata.account.model.dto.AccountDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IAccountService {
	
	Flux<AccountDto> getAccounts();
	
    Mono<AccountDto> getAccountById(String id);
    
    Mono<AccountDto> saveAccount(Mono<AccountDto> accountDtoMono);
    
    Mono<AccountDto> updateAccount(Mono<AccountDto> accountDtoMono, String id);
    
    Mono<Void> deleteAccount(String id);
    
    Mono<Account> findByAccountNumber(String accountNumber);
   

}

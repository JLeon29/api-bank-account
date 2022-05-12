package com.nttdata.account.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.account.models.Account;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface AccountRepository extends ReactiveMongoRepository<Account, String>{
	
	 Flux<Account> findAllByCustomerIdentityNumber(String customerIdentityNumber);
	 Mono<Account> findByCustomerIdentityNumber(String customerIdentityNumber);
	 Mono<Account> findByAccountNumber(String accountNumber);

}

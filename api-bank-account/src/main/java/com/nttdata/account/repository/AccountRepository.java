package com.nttdata.account.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.account.model.Account;
import com.nttdata.account.model.Client;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface AccountRepository extends ReactiveMongoRepository<Account, String>{
	
	Mono<Account> findByAccountNumber (String accountIdNumber);

	
	

}

package com.nttdata.account.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.nttdata.account.app.utils.AppUtils;
import com.nttdata.account.feign.ClientFeign;
import com.nttdata.account.model.Account;
import com.nttdata.account.model.Client;
import com.nttdata.account.model.dto.AccountDto;
import com.nttdata.account.repository.AccountRepository;
import com.nttdata.account.repository.ClientRepository;
import com.nttdata.account.service.IAccountService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountServiceImpl implements IAccountService {

	@Autowired
	private AccountRepository repository;
	
	@Autowired
	private ClientRepository repositoryClient;
	
	/*@Autowired
	private ClientFeign feign;*/

	@Override
	public Flux<AccountDto> getAccounts() {
		return repository.findAll().map(AppUtils::entityToDto);
	}

	@Override
	public Mono<AccountDto> getAccountById(String id) {
		return repository.findById(id).map(AppUtils::entityToDto);
	}

	@Override
	public Mono<AccountDto> saveAccount(Mono<AccountDto> accountDtoMono) {
        
		
		
		return accountDtoMono.map(AppUtils::dtoToEntity)
                     .flatMap(repository::insert)
                     .map(AppUtils::entityToDto)
                     ;
 
    }
	
	@Override
	public Mono<AccountDto> updateAccount(Mono<AccountDto> accountDtoMono, String id) {
		return repository.findById(id)
				.flatMap(a -> accountDtoMono.map(AppUtils::dtoToEntity).doOnNext(ac -> ac.setId(id)))
				.flatMap(repository::save).map(AppUtils::entityToDto);
	}

	@Override
	public Mono<Void> deleteAccount(String id) {
		return repository.deleteById(id);
	}

	@Override
	public Mono<Account> findByAccountNumber(String accountNumber) {
		return repository.findByAccountNumber(accountNumber);
	}

	




}

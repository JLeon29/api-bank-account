package com.nttdata.account.service;

import com.nttdata.account.model.Client;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IClientService {
	
	Flux<Client> findAll();
	Mono<Client> findById(String id);
	Mono<Client> save(Client client);
	Mono<Client> update(Client client, String id);
	Mono<Void> delete(String id);
	

}

package com.nttdata.account.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.nttdata.account.model.Client;
import com.nttdata.account.service.IClientService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ClientServiceImpl implements IClientService {
	
	@Autowired
	private WebClient webClient;

	@Override
	public Flux<Client> findAll() {
		return webClient.get().accept(MediaType.APPLICATION_JSON_UTF8)
				.exchange()
				.flatMapMany(response -> response.bodyToFlux(Client.class));
	}

	@Override
	public Mono<Client> findById(String id) {
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("id", id);
		return webClient.get().uri("/{id}", params)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.retrieve()
				.bodyToMono(Client.class);
	}

	@Override
	public Mono<Client> save(Client client) {
		return webClient.post()
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.contentType(MediaType.APPLICATION_PROBLEM_JSON_UTF8)
				//.body(BodyInserters.fromObject(client)) la otra forma es el .syncBody(client)
				.syncBody(client)
				.retrieve()
				.bodyToMono(Client.class);
	}

	@Override
	public Mono<Client> update(Client client, String id) {
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("id", id);
		return webClient.put()
				.uri("/{id}", Collections.singletonMap("id", id))
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.contentType(MediaType.APPLICATION_PROBLEM_JSON_UTF8)
				.syncBody(client)
				.retrieve()
				.bodyToMono(Client.class);
	}

	@Override
	public Mono<Void> delete(String id) {
		return webClient.delete()
				.uri("/{id}", Collections.singletonMap("id", id))
				.exchange().then();
	}

}

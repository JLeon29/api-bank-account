package com.nttdata.account.handler;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import static org.springframework.http.MediaType.*;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import com.nttdata.account.model.Client;
import com.nttdata.account.service.IClientService;


import reactor.core.publisher.Mono;

@Component
public class ClientHandler {
	
	@Autowired
	private IClientService service;
	
	public Mono<ServerResponse> listar(ServerRequest request){
		
		return ServerResponse.ok().contentType(APPLICATION_JSON_UTF8)
				.body(service.findAll(), Client.class);
	}
	
	public Mono<ServerResponse> ver(ServerRequest request){
		String id = request.pathVariable("id");
		return service.findById(id).flatMap(p -> ServerResponse.ok()
				.contentType(APPLICATION_JSON_UTF8)
				.syncBody(p))
				.switchIfEmpty(ServerResponse.notFound().build());
	}
	
	public Mono<ServerResponse> crear(ServerRequest request){
		Mono<Client> client = request.bodyToMono(Client.class);
		
		return client.flatMap(p-> 
			service.save(p)
			).flatMap(p -> ServerResponse.created(URI.create("/api/client/".concat(p.getId())))
					.contentType(APPLICATION_JSON_UTF8)
					.syncBody(p))
				.onErrorResume(error -> {
					WebClientResponseException errorResponse = (WebClientResponseException) error;
					if(errorResponse.getStatusCode() == HttpStatus.BAD_REQUEST) {
						return ServerResponse.badRequest()
								.contentType(APPLICATION_JSON_UTF8)
								.syncBody(errorResponse.getResponseBodyAsString());
					}
					return Mono.error(errorResponse);
				});
		/*
		return client.flatMap(p-> {
			if(p.getAddress()==null) {
				p.setAddress("Lima/Peru");
			}
			return service.save(p);
			}).flatMap(p -> ServerResponse.created(URI.create("/api/client/".concat(p.getId())))
					.contentType(APPLICATION_JSON_UTF8)
					.syncBody(p))
				.onErrorResume(error -> {
					WebClientResponseException errorResponse = (WebClientResponseException) error;
					if(errorResponse.getStatusCode() == HttpStatus.BAD_REQUEST) {
						return ServerResponse.badRequest()
								.contentType(APPLICATION_JSON_UTF8)
								.syncBody(errorResponse.getResponseBodyAsString());
					}
					return Mono.error(errorResponse);
				});
		*/
	}
	
	public Mono<ServerResponse> editar(ServerRequest request){
		Mono<Client> client = request.bodyToMono(Client.class);
		String id = request.pathVariable("id");
		
		return errorHandler(
				client
				.flatMap(p -> service.update(p, id))
				.flatMap(p-> ServerResponse.created(URI.create("/api/client/".concat(p.getId())))
				.contentType(APPLICATION_JSON_UTF8)
				.syncBody(p))
				);
	}
	
	public Mono<ServerResponse> eliminar(ServerRequest request){
		String id = request.pathVariable("id");
		return errorHandler(
				service.delete(id).then(ServerResponse.noContent().build())
				);
	}
	
	
	private Mono<ServerResponse> errorHandler(Mono<ServerResponse> response){
		return response.onErrorResume(error -> {
			WebClientResponseException errorResponse = (WebClientResponseException) error;
			if(errorResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
				Map<String, Object> body = new HashMap<>();
				body.put("error", "No existe el producto: ".concat(errorResponse.getMessage()));
				
				body.put("status", errorResponse.getStatusCode().value());
				return ServerResponse.status(HttpStatus.NOT_FOUND).syncBody(body);
			}
			return Mono.error(errorResponse);
		});
	}

}

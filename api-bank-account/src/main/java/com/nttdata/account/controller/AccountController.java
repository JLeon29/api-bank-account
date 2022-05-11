package com.nttdata.account.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebExchangeBindException;

import com.nttdata.account.model.dto.AccountDto;
import com.nttdata.account.service.IAccountService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/account")
public class AccountController {
	
	@Autowired
	private IAccountService service;
	
	@GetMapping
	public Mono<ResponseEntity<Flux<AccountDto>>> getClients(){
		return Mono.just(
				ResponseEntity.ok() 
				.contentType(MediaType.APPLICATION_JSON_UTF8) 
				.body(service.getAccounts())
				);
	}
	
	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<AccountDto>> getClient(@PathVariable String id){
		return service.getAccountById(id).map(p -> ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(p))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@PostMapping
	public Mono<ResponseEntity<Map<String, Object>>> crear(@Valid @RequestBody Mono<AccountDto> accountDto){
		
		Map<String, Object> respuesta = new HashMap<String, Object>();
		
		return service.saveAccount(accountDto).map(c-> {
			respuesta.put("account", c);
			respuesta.put("mensaje", "Account Created successfully");
			
			return ResponseEntity
				.created(URI.create("/api/account/".concat(c.getId())))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(respuesta);
			
			
			}).onErrorResume(t -> {
				return Mono.just(t).cast(WebExchangeBindException.class)
						.flatMap(e -> Mono.just(e.getFieldErrors()))
						.flatMapMany(Flux::fromIterable)
						.map(fieldError -> "El campo "+fieldError.getField() + " " + fieldError.getDefaultMessage())
						.collectList()
						.flatMap(list -> {
							respuesta.put("errors", list);
							respuesta.put("status", HttpStatus.BAD_REQUEST.value());
							return Mono.just(ResponseEntity.badRequest().body(respuesta));
						});
			
			
			});
	
		}



	@PutMapping("/{id}")
	public Mono<AccountDto> updateClient(@RequestBody Mono<AccountDto> accountDto, @PathVariable String id) {
		return service.updateAccount(accountDto, id);
	}


	@DeleteMapping("/{id}")
	public Mono<Void> deleteClient(@PathVariable String id) {
		return service.deleteAccount(id);
	}



}

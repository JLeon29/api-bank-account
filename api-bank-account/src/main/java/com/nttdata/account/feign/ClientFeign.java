package com.nttdata.account.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.nttdata.account.model.dto.ClientDto;

import reactor.core.publisher.Mono;

@FeignClient(name = "service-client")
public interface ClientFeign {
	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<ClientDto>> getClient(@PathVariable String id);

}

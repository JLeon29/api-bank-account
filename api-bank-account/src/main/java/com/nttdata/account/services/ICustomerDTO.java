package com.nttdata.account.services;

import com.nttdata.account.dto.Customer;

import reactor.core.publisher.Mono;

public interface ICustomerDTO {
	
	Mono<Customer> getCustomer(String customerIdentityNumber);
	Mono<Customer> newPan(String id, Customer customerDTO);

}

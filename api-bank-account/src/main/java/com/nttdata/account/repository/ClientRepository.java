package com.nttdata.account.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.account.model.Client;

@Repository
public interface ClientRepository extends ReactiveMongoRepository<Client, String> {
	


}

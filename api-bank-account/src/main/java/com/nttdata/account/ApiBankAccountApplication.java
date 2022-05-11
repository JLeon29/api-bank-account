package com.nttdata.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.nttdata.account.service.IAccountService;

@EnableDiscoveryClient
@SpringBootApplication
public class ApiBankAccountApplication implements CommandLineRunner {
	
	@Autowired
	private IAccountService service;
	
	private static final Logger log = LoggerFactory.getLogger(ApiBankAccountApplication.class);
	
	@Autowired
	private ReactiveMongoTemplate mongoTemplate;

	public static void main(String[] args) {
		SpringApplication.run(ApiBankAccountApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		/*
		mongoTemplate.dropCollection("account").subscribe();

		Flux.just(new Account("235262684159", "SOLES", "Ahorro",null),
						new Account("856525102545", "SOLES", "Cuenta Corriente", null),
						new Account("985632100025", "SOLES", "Plazo Fijo", null),
						new Account("232154963258", "SOLES", "Ahorro", null)
						
						)
			
		
		.subscribe(account -> log.info("Insert: " + account.getAccountNumber() + " " + account.getAccountType()));*/
		
	}
	

}

package com.nttdata.account.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.nttdata.account.dto.CustomerDTO;
import com.nttdata.account.dto.Titular;
import com.nttdata.account.models.Account;
import com.nttdata.account.services.IAccountService;
import com.nttdata.account.services.ICreditService;
import com.nttdata.account.services.ICustomerDTO;

import reactor.core.publisher.Mono;

@Configuration
public class AccountHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountHandler.class);
    @Autowired
    private IAccountService service;
    @Autowired
    private ICustomerDTO customerService;
    @Autowired
    private ICreditService creditService;
    
    public Mono<ServerResponse> findAll(ServerRequest request){
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(service.findAll(), Account.class);
    }

    public Mono<ServerResponse> findByAccountNumber(ServerRequest request) {
        String accountNumber = request.pathVariable("accountNumber");
        LOGGER.info("El AccountNumber es " + accountNumber);
        return service.findByAccountNumber(accountNumber).flatMap(c -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(c)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }


    public Mono<ServerResponse> newCurrentAccount(ServerRequest request){

        Mono<Account> accountMono = request.bodyToMono(Account.class);

        return accountMono.flatMap( account -> customerService.getCustomer(account.getCustomerIdentityNumber())
                        .flatMap(customer -> customerService.getCustomer(account.getTitulares().get(0).getDni()))
                        .flatMap(customer ->
                        {
                            account.getTitulares().get(0).setName(customer.getName());
                            return customerService.getCustomer(account.getCustomerIdentityNumber());
                        })
                        .flatMap(titular -> customerService.getCustomer(account.getCustomerIdentityNumber())
                        )
                        .flatMap(customer -> {
                            account.setTypeOfAccount("CURRENT_ACCOUNT");
                            account.setMaxLimitMovementPerMonth(account.getMaxLimitMovementPerMonth());
                            account.setMovementPerMonth(0);
                            LOGGER.info("El customer type es:{}" + customer.getCustomerType().getCode());
                            account.setCustomer(CustomerDTO.builder()
                                    .name(customer.getName()).code(customer.getCustomerType().getCode())
                                    .customerIdentityNumber(customer.getCustomerIdentityNumber()).build());
                            if(customer.getCustomerType().getCode().equals("2001")
                                    ||customer.getCustomerType().getCode().equals("2002")) {
                                return service.create(account);
                            }else {
                                return creditService.validateDebtorCredit(account.getCustomerIdentityNumber())
                                        .flatMap(debtor -> {
                                            if(debtor == true) {
                                                return Mono.empty();
                                            }else return service.validateCustomerIdentityNumber(account.getCustomerIdentityNumber());
                                        })
                                        .flatMap(accountFound -> {
                                            account.setFirmantes(null);
                                            if (accountFound.getCustomerIdentityNumber() != null) {
                                                LOGGER.info("La cuenta encontrada es: " + accountFound.getCustomerIdentityNumber());
                                                return Mono.error(new RuntimeException("THERE IS AN ACCOUNT WITH THIS CUSTOMER ALREADY"));
                                            } else if (!account.getCustomer().getCustomerIdentityNumber()
                                                    .equals(account.getTitulares().get(0).getDni())) {
                                                LOGGER.info("El dni del itular y el cliente son diferentes cuando el customer es personal ");
                                                return Mono.error(new RuntimeException("THERE IS AN ACCOUNT WITH THIS CUSTOMER ALREADY"));
                                            } else {
                                                LOGGER.info("No encontró nada: ");
                                                return service.create(account);
                                            }
                                        });
                            }
                        }))
                .flatMap( c -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(c))
                ).switchIfEmpty(ServerResponse.badRequest().build());
    }

    public Mono<ServerResponse> findByCustomerIdentityNumber(ServerRequest request) {
        String customerIdentityNumber = request.pathVariable("customerIdentityNumber");
        return service.findByCustomerIdentityNumber(customerIdentityNumber)
                .flatMap(c -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(c)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> addCardHolder(ServerRequest request){
        String accountNumber = request.pathVariable("accountNumber");
        Mono<Titular> titularMono = request.bodyToMono(Titular.class);
        LOGGER.info("El AccountNumber es " + accountNumber);
        return titularMono
                .flatMap(titular -> customerService.getCustomer(titular.getDni())
                        .flatMap(cardHolder -> service.findByAccountNumber(accountNumber))
                        .filter(currentAccount -> currentAccount.getCustomer().getCode().equals("2001")
                                ||currentAccount.getCustomer().getCode().equals("2002"))
                        .flatMap(currentAccount -> {
                            currentAccount.getTitulares().add(titular);
                            return service.update(currentAccount);
                        }))
                .flatMap( c -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(c))
                ).switchIfEmpty(ServerResponse.notFound().build());
    }


    public Mono<ServerResponse> findAllByCustomerIdentityNumber(ServerRequest request){
        String customerIdentityNumber = request.pathVariable("customerIdentityNumber");
        return  ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(service.findAllByCustomerIdentityNumber(customerIdentityNumber), Account.class);
    }

    public Mono<ServerResponse> deleteCurrentAccount(ServerRequest request){

        String id = request.pathVariable("id");

        Mono<Account> accountMono = service.findById(id);

        return accountMono
                .doOnNext(c -> LOGGER.info("deleteConsumption: consumptionId={}", c.getId()))
                .flatMap(c -> service.delete(c).then(ServerResponse.noContent().build()))
                .switchIfEmpty(ServerResponse.notFound().build());
    }


    public Mono<ServerResponse> updateCurrentAccount(ServerRequest request){
        Mono<Account> accountMono = request.bodyToMono(Account.class);
        String id = request.pathVariable("id");

        return service.findById(id).zipWith(accountMono, (db,req) -> {
                    db.setAmount(req.getAmount());
                    db.setMovementPerMonth((req.getMovementPerMonth()));
                    return db;
                }).flatMap( c -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(service.update(c),Account.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

}

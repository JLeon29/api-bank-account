package com.nttdata.account.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import com.nttdata.account.handlers.AccountHandler;

@Configuration
public class RouterConfig {
	
	@Bean
    public RouterFunction<ServerResponse> routes(AccountHandler accountHandler){

        return route(RequestPredicates.GET("/api/currentAccount"), accountHandler::findAll)
                .andRoute(GET("/api/currentAccount/{customerIdentityNumber}"),accountHandler::findAllByCustomerIdentityNumber)
                .andRoute(GET("/api/currentAccount/customer/{customerIdentityNumber}"),accountHandler::findByCustomerIdentityNumber)
                .andRoute(GET("/api/currentAccount/account/{accountNumber}"), accountHandler::findByAccountNumber)
                .andRoute(PUT("/api/currentAccount/holder/{accountNumber}"), accountHandler::addCardHolder)
                .andRoute(POST("/api/currentAccount"), accountHandler::newCurrentAccount)
                .andRoute(PUT("/api/currentAccount/{id}"), accountHandler::updateCurrentAccount)
                .andRoute(DELETE("/api/currentAccount/{id}"), accountHandler::deleteCurrentAccount);
    }

}

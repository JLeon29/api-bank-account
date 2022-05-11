package com.nttdata.account;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.nttdata.account.handler.ClientHandler;

@Configuration
public class RouterConfig {
	
	@Bean
	public RouterFunction<ServerResponse> rutas(ClientHandler handler){
		return RouterFunctions.route(RequestPredicates.GET("/api/client"), handler::listar)
				.andRoute(RequestPredicates.GET("/api/v2/client/{id}"), handler::ver);
	}

}

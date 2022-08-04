package com.canaparro.reactiveservice.websocket;

import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;

import lombok.extern.log4j.Log4j2;

@Configuration
@Log4j2
public class WebSocketConfiguration {

	@Bean
	public Executor executor() {
		return Executors.newSingleThreadExecutor();
	}

	@Bean
	HandlerMapping handlerMapping( WebSocketHandler wsh ) {
		return new SimpleUrlHandlerMapping( Map.of( "/ws/users", wsh ), 10 );
	}

}

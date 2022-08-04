package com.canaparro.reactiveservice.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;

import com.canaparro.reactiveservice.user.UserRecordCreatedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Log4j2
public class EventToSessionHandler implements WebSocketHandler {

	private final ObjectMapper objectMapper;
	private final UserRecordEventPublisher eventPublisher;

	@Override
	public Mono<Void> handle( WebSocketSession session ) {
		Flux<WebSocketMessage> publish = Flux.create( eventPublisher )
				.share()
				.map( this::marshalEventSource )
				.map( session::textMessage );

		return session.send( publish );
	}

	private String marshalEventSource( final UserRecordCreatedEvent evt ) {
		try {
			return objectMapper.writeValueAsString( evt.getSource() );
		} catch ( JsonProcessingException e ) {
			throw new RuntimeException( e );
		}
	}
}
package com.canaparro.reactiveservice.user;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
public class UserDataInitializer {

	private final ReactiveCrudRepository<UserRecord, Long> repository;

	@EventListener(ApplicationReadyEvent.class)
	public void databaseInitializer() {
		Flux.just( "Andreas", "Carlos", "Franco" )
				.map( name -> new UserRecord( null, name ) )
				.flatMap( repository::save )
				.subscribe();
	}
}

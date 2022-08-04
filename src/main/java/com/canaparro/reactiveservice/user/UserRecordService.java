package com.canaparro.reactiveservice.user;

import java.time.Duration;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserRecordService {

	private final ReactiveCrudRepository<UserRecord, Long> repository;
	private final ApplicationEventPublisher eventPublisher;

	public Flux<UserRecord> findAllUsers() {
		return repository.findAll();
	}

	public Mono<UserRecord> createUser( @PathVariable final UserRecord userRecord ) {
		return repository.save( userRecord )
				.doOnSuccess( r -> eventPublisher.publishEvent( new UserRecordCreatedEvent( r ) ) );
	}

	public Mono<UserRecord> findUserById( final long id ) {
		return repository.findById( id );
	}

	public void updateUser( final long id, final UserRecord user ) {
		user.setId( id );
		repository.save( user );
	}

	public void delete( final long id ) {
		repository.deleteById( id );
	}
}

package com.canaparro.reactiveservice.user;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.reactivestreams.Publisher;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@Profile("mock")
public class UserRepositoryStub implements ReactiveCrudRepository<UserRecord, Long> {

	private final List<UserRecord> people = new ArrayList<>(
			List.of( new UserRecord( 1L, "Marius" ), new UserRecord( 2L, "Denisa" ), new UserRecord( 3L, "Marcelo" ),
					new UserRecord( 4L, "Andreas" ), new UserRecord( 5L, "Franco" ), new UserRecord( 6L, "Paulo" ),
					new UserRecord( 7L, "Carlos" ) ) );


	public Flux<UserRecord> findAll() {
		return Flux.fromStream( people.stream() ).delayElements( Duration.ofSeconds( 1 ) );
	}

	public Mono<UserRecord> save( final UserRecord userRecord ) {
		return Mono.just(userRecord);
	}

	@Override
	public <S extends UserRecord> Flux<S> saveAll( final Iterable<S> entities ) {
		return null;
	}

	@Override
	public <S extends UserRecord> Flux<S> saveAll( final Publisher<S> entityStream ) {
		return null;
	}

	@Override
	public Mono<UserRecord> findById( final Long aLong ) {
		return null;
	}

	@Override
	public Mono<UserRecord> findById( final Publisher<Long> id ) {
		return null;
	}

	@Override
	public Mono<Boolean> existsById( final Long aLong ) {
		return null;
	}

	@Override
	public Mono<Boolean> existsById( final Publisher<Long> id ) {
		return null;
	}

	@Override
	public Flux<UserRecord> findAllById( final Iterable<Long> longs ) {
		return null;
	}

	@Override
	public Flux<UserRecord> findAllById( final Publisher<Long> idStream ) {
		return null;
	}

	@Override
	public Mono<Long> count() {
		return null;
	}

	@Override
	public Mono<Void> deleteById( final Long aLong ) {
		return null;
	}

	@Override
	public Mono<Void> deleteById( final Publisher<Long> id ) {
		return null;
	}

	@Override
	public Mono<Void> delete( final UserRecord entity ) {
		return null;
	}

	@Override
	public Mono<Void> deleteAllById( final Iterable<? extends Long> longs ) {
		return null;
	}

	@Override
	public Mono<Void> deleteAll( final Iterable<? extends UserRecord> entities ) {
		return null;
	}

	@Override
	public Mono<Void> deleteAll( final Publisher<? extends UserRecord> entityStream ) {
		return null;
	}

	@Override
	public Mono<Void> deleteAll() {
		return null;
	}
}

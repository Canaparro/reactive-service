package com.canaparro.reactiveservice.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@DataR2dbcTest
@Import(UserRecordService.class)
class UserRecordServiceTest {

	private final UserRecordService service;

	private final UserRepositoryImpl repository;

	public UserRecordServiceTest( @Autowired UserRecordService service,
			@Autowired final UserRepositoryImpl repository ) {
		this.service = service;
		this.repository = repository;
	}

	@BeforeEach
	void setUp() {
		repository.deleteAll().subscribe();
	}

	@Test
	@DisplayName("Given a user record when creating should return a persisted entity")
	void givenAUserRecord_whenCreating_shouldReturnAPersistedEntity() {
		// Given
		String name = "Marius";

		// When
		Mono<UserRecord> persisted = service.createUser( new UserRecord( null, name ) );

		// Then
		StepVerifier.create( persisted )
				.expectNextMatches( user -> name.equals( user.getName() ) && user.getId() != null )
				.verifyComplete();
	}

	@Test
	@DisplayName("Given two persisted users when finding all should return them")
	void givenTwoPersistedUsers_whenFindingAll_shouldReturnThem() {
		// Given
		String firstName = "Marius";
		String secondName = "Denisa";

		// When
		Flux<UserRecord> persistedUsers = service.createUser( new UserRecord( null, firstName ) )
				.thenMany( service.createUser( new UserRecord( null, secondName ) ) )
				.thenMany( service.findAllUsers() );

		// Then
		StepVerifier.create( persistedUsers )
				.expectNextMatches( user -> firstName.equals( user.getName() ) )
				.expectNextMatches( user -> secondName.equals( user.getName() ) )
				.verifyComplete();

	}
}
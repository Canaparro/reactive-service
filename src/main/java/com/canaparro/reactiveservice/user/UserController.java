package com.canaparro.reactiveservice.user;

import java.net.URI;

import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.canaparro.reactiveservice.websocket.UserRecordEventPublisher;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Log4j2
public class UserController {

	private final UserRecordService userService;
	private final UserRecordEventPublisher eventPublisher;

	@GetMapping
	public Publisher<UserRecord> findAllUsers() {
		return userService.findAllUsers();
	}

	@GetMapping("/{id}")
	public Publisher<UserRecord> findUserById( @PathVariable final long id ) {
		return userService.findUserById( id );
	}

	@PostMapping
	public Publisher<ResponseEntity<UserRecord>> createUserRecord(
			@RequestBody final UserRecord userRecord ) {
		return userService.createUser( userRecord )
				.map( r -> ResponseEntity.created( URI.create( "/users/" + r.getId() ) )
						.contentType( MediaType.APPLICATION_JSON )
						.build() );
	}

	@PutMapping("{id}")
	public void updateUser( @PathVariable final long id, @RequestBody final UserRecord user ) {
		userService.updateUser( id, user );
	}

	@DeleteMapping("/{id}")
	public void delete( @PathVariable final long id ) {
		userService.delete( id );
	}

	@GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Publisher<UserRecord> findAllUsersStream() {
		return userService.findAllUsers();
	}

	@GetMapping(value = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Publisher<UserRecordCreatedEvent> sse() {
		return Flux.create( eventPublisher );
	}

}

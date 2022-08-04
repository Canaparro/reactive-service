package com.canaparro.reactiveservice.performance;

import java.time.Duration;
import java.util.List;

import org.reactivestreams.Publisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.canaparro.reactiveservice.user.UserRecord;

import reactor.core.publisher.Mono;

@RestController
public class PerformanceController {

	private final List<UserRecord> users = List.of( new UserRecord( 1L, "Marius" ),
			new UserRecord( 2L, "Denisa" ), new UserRecord( 3L, "Marcelo" ),
			new UserRecord( 4L, "Andreas" ), new UserRecord( 5L, "Franco" ),
			new UserRecord( 6L, "Paulo" ), new UserRecord( 7L, "Carlos" ) );

	@GetMapping("/performance/{delay}")
	public Publisher<List<UserRecord>> findAllUsers(@PathVariable int delay ) {
		return Mono.just( users ).delayElement( Duration.ofMillis( delay ) );
	}
}

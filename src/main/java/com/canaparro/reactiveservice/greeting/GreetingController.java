package com.canaparro.reactiveservice.greeting;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@Log4j2
public class GreetingController {

	@GetMapping(value = "/stream/greetings/{name}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Publisher<GreetingResponse> getGreeting( @PathVariable("name") final String name ) {
		return this.greet( name );
	}

	private Publisher<GreetingResponse> greet( final String name ) {
		return Flux.fromStream(
						Stream.generate( () -> "Hello " + name + " @ " + LocalDateTime.now() ) )
				.map( GreetingResponse::new )
				.doOnNext( log::info )
				.delayElements( Duration.ofSeconds( 1 ) );
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	static class GreetingResponse {
		private String greeting;
	}
}

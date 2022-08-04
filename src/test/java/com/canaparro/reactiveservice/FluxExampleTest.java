package com.canaparro.reactiveservice;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
class FluxExampleTest {

	@Test
	void printRandomLongTest() {
		printNumbers( 1, "one" );
		printNumbers( 1, "two" );

		TimeUtils.sleepSeconds( 10 );
	}

	void printNumbers( final int time, final String name ) {
		Flux.range( 1, 8 )
				.delayElements( Duration.ofSeconds( time ) )
				.map( i -> "This is method " + name + " value: " + i )
				.subscribe( log::info );
	}
}

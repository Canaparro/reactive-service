package com.canaparro.reactiveservice;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Log4j2
class MonoExampleTest {

	@Test
	void printRandomIntTest() {
		printRandomInt(1);

		TimeUtils.sleepSeconds(10);
	}

	void printRandomInt(final int time) {
		Mono.fromCallable( this::getRandomInt )
				.delayElement( Duration.ofSeconds( time ) )
				.doOnNext( v -> log.info( "time: {}", time ) )
				.map( i -> (char) i.intValue())
				.subscribe( log::info );
	}

	private int getRandomInt() {
		return (int) ((Math.random() * (122 - 97)) + 97);
	}
}

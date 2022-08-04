package com.canaparro.reactiveservice.websocket;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import com.canaparro.reactiveservice.user.UserRecordCreatedEvent;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.FluxSink;

@Component
@RequiredArgsConstructor
public class UserRecordEventPublisher implements ApplicationListener<UserRecordCreatedEvent>,
		Consumer<FluxSink<UserRecordCreatedEvent>> {

	private final Executor executor;
	private final BlockingQueue<UserRecordCreatedEvent> queue = new LinkedBlockingQueue<>();

	@Override
	public void onApplicationEvent( UserRecordCreatedEvent event ) {
		this.queue.offer( event );
	}

	@Override
	public void accept( FluxSink<UserRecordCreatedEvent> sink ) {
		this.executor.execute( () -> {
			while ( true )
				try {
					UserRecordCreatedEvent event = queue.take();
					sink.next( event );
				} catch ( InterruptedException e ) {
					ReflectionUtils.rethrowRuntimeException( e );
				}
		} );
	}
}

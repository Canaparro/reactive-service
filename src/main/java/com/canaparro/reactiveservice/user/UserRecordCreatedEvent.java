package com.canaparro.reactiveservice.user;

import org.springframework.context.ApplicationEvent;

public class UserRecordCreatedEvent extends ApplicationEvent {

	public UserRecordCreatedEvent( final Object source ) {
		super( source );
	}
}

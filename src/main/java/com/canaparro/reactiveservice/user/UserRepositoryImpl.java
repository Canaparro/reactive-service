package com.canaparro.reactiveservice.user;

import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

@Profile( "prod" )
public interface UserRepositoryImpl extends ReactiveCrudRepository<UserRecord, Long> {}

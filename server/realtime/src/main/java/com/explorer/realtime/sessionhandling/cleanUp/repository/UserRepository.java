package com.explorer.realtime.sessionhandling.cleanUp.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository("cleanUpUserRepository")
@RequiredArgsConstructor
public class UserRepository {

    private final ReactiveHashOperations<String, Object, Object> reactiveHashOperations;

    private static final String KEY_PREFIX = "user:";
    public Mono<Boolean> delete(Long userId) {
        return reactiveHashOperations.delete(KEY_PREFIX + userId);
    }
}
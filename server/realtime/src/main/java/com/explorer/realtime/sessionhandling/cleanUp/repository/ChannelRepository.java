package com.explorer.realtime.sessionhandling.cleanUp.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository("cleanUpChannelRepository")
@RequiredArgsConstructor
public class ChannelRepository {

    private final ReactiveRedisTemplate<String, String> reactiveRedisTemplate;

    private static final String KEY_PREFIX = "channel:";

    public Mono<Boolean> delete(String channelId) {
        return reactiveRedisTemplate.opsForSet().delete(KEY_PREFIX + channelId);
    }
}
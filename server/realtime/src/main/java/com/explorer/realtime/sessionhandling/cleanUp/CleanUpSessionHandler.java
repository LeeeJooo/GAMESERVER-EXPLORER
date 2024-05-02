package com.explorer.realtime.sessionhandling.cleanUp;

import com.explorer.realtime.global.component.session.SessionManager;
import com.explorer.realtime.sessionhandling.cleanUp.repository.ChannelRepository;
import com.explorer.realtime.sessionhandling.cleanUp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;

@Slf4j
@Component
@RequiredArgsConstructor
public class CleanUpSessionHandler {

    private final SessionManager sessionManager;
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;

    public Mono<Void> cleanUpHandler(Connection connection) {

        log.info("Start clean up...");

        // 1. get userId
        String userId = sessionManager.getUid(connection);

        // 2. channelId 조회 및 redis-connection에서 channelId가 key인 hashTable 수정 및 삭제
        String channelId = "abcd";
        Mono<Void> channelDeleteMono = channelRepository.delete(channelId).then();

        // 3. redis-connection에서 userId가 key인 hashTable 삭제
        Mono<Void> userDeleteMono =  userRepository.delete(Long.valueOf(userId)).then();

        // 4. SessionManager로 connection 정보 삭제
        Mono<Void> connectionDeleteMono = Mono.fromRunnable(() -> sessionManager.removeConnection(userId));

        return Mono.when(channelDeleteMono, userDeleteMono, connectionDeleteMono);

    }

}

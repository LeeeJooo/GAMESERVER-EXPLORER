package Explorer.realtimeserver.serverManagement;

import Explorer.realtimeserver.redisConnection.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.netty.NettyInbound;
import reactor.netty.NettyOutbound;

@Component
public class MessageHandler {

    private static final Logger log = LoggerFactory.getLogger(MessageHandler.class);    // Logger
    @Autowired
    private RedisService redisService;

    public Mono<Void> handleMessage(NettyInbound inbound, NettyOutbound outbound) {

        return inbound.receive()
                .asString()
                .flatMap(msg -> {
                    /*
                     * redis 데이터 조회
                     * key(connection) 으로 value(info)를 조회
                     */
                    inbound.withConnection(connection -> log.info("{} : {}", connection, redisService.readFromRedis(connection.toString())));  // 메세지를 보낸 클라이언트의 connection

                    log.info("Received data : {}", msg);        // 수신한 메시지 내용
                    return outbound.sendString(Mono.just(msg)); // 클라이언트에 송신

                    /* 송신 성공 여부 확인
                    return outbound.sendString(Mono.just(msg))
                            .then() // 성공적으로 데이터 송신 시
                            .doOnSuccess(aVoid -> log.info("Successfully sent 'success' message to client"))
                            .doOnError(error -> log.error("Failed to send 'success' message: ", error));

                     */
                })
                .then();
    }

}

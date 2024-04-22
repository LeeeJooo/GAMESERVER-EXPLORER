package Explorer.realtimeserver.serverManagement;

import Explorer.realtimeserver.redisConnection.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.netty.Connection;

import java.util.function.Consumer;

@Component
public class ConnectionHandler implements Consumer<Connection> {


    private String info = "client";
    private static final Logger log = LoggerFactory.getLogger(ConnectionHandler.class);

    @Autowired
    private RedisService redisService;

    @Override
    public void accept(Connection connection) {
        // client 연결 시 Connection 정보 출력
        log.info("New connection established: {}", connection.channel().remoteAddress());

        // redis에 connection 정보 저장
        redisService.saveToRedis(connection.toString(), info);
    }
}

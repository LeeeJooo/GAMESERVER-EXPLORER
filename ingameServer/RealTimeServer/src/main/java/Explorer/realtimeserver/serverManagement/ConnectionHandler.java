package Explorer.realtimeserver.serverManagement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.netty.Connection;

import java.util.function.Consumer;

@Component
public class ConnectionHandler implements Consumer<Connection> {


    private static final Logger log = LoggerFactory.getLogger(ConnectionHandler.class);

    @Override
    public void accept(Connection connection) {
        // client 연결 시 Connection 정보 출력
        log.info("New connection established: {}", connection.channel().remoteAddress());
    }
}

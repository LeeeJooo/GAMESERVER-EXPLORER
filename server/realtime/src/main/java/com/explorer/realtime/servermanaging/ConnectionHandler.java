package com.explorer.realtime.servermanaging;

import com.explorer.realtime.sessionhandling.cleanUp.CleanUpSessionHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.netty.Connection;

import java.util.function.Consumer;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConnectionHandler implements Consumer<Connection> {

    private final CleanUpSessionHandler cleanUpSessionHandler;

    @Override
    public void accept(Connection connection) {

        connection.addHandlerFirst(new ChannelHandlerAdapter() {

            @Override
            public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

                super.handlerRemoved(ctx);
                log.info("connection lost First: {}", connection.channel().remoteAddress());
                cleanUpSessionHandler.cleanUpHandler(connection).subscribe();
            }

        });

    }
}

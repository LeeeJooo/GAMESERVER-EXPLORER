package com.explorer.chat.servermanaging;

import com.explorer.chat.eventhandling.channelhandling.ChannelHandler;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.netty.NettyInbound;
import reactor.netty.NettyOutbound;

@Component
@RequiredArgsConstructor
public class RequestHandler {

    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private final ChannelHandler channelHandler;

    public Mono<Void> handleRequest(NettyInbound inbound, NettyOutbound outbound) {

        return inbound
                .receive()
                .asString()
                .flatMap(msg -> {

                    log.info("Received message: {}", msg);

                    try {

                        JSONObject json = new JSONObject(msg);

                        inbound.withConnection(connection -> {

                            String type = json.getString("type");

                            switch (type) {
                                case "channel":
                                    log.info("channel");
                                    Mono.fromRunnable(() -> channelHandler.channelHandler(json, connection))
                                            .subscribeOn(Schedulers.boundedElastic())
                                            .subscribe();
                                    break;

                                case "chat":
                                    break;
                            }

                        });

                        return outbound.sendString(Mono.just(msg));

                    } catch (JSONException e) {

                        log.error("ERROR: {}", e.getMessage());
                        return Mono.empty();

                    }

                })
                .then();
    }
}

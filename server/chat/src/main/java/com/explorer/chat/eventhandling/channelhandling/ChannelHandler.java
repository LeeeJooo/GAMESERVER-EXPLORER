package com.explorer.chat.eventhandling.channelhandling;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import reactor.netty.Connection;

@Slf4j
@Component
public class ChannelHandler {

    public void channelHandler(JSONObject json, Connection connection) {

        String event = json.getString("event");

        switch (event) {
            case "channelIn":
                log.info("channelIn");
                break;

            case "channelOut":
                break;
        }

    }
}

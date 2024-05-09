package com.explorer.realtime.gamedatahandling.laboratory.event;

import com.explorer.realtime.gamedatahandling.laboratory.dto.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class Decompose {

    public Mono<Void> process(JSONObject json) {
        /*
         * 1) Parsing, create UserInfo
         */
        UserInfo userInfo = UserInfo.of(json);
        log.info("userInfo : {} in channel {}", userInfo.getUserId(), userInfo.getChannelId());

        /*
         * 2) redis-ingame labData에서 합성물이 있는지 확인
         */

        return Mono.empty();

    }
}

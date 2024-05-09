package com.explorer.realtime.gamedatahandling.laboratory;

import com.explorer.realtime.gamedatahandling.laboratory.event.Decompose;
import com.explorer.realtime.gamedatahandling.laboratory.event.Extract;
import com.explorer.realtime.gamedatahandling.laboratory.event.Synthesize;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class LaboratoryHandler {

    private final Extract extract;
    private final Synthesize synthesize;
    private final Decompose decompose;

    public Mono<Void> laboratoryHandler(JSONObject json) {
        String eventName = json.getString("eventName");

        switch (eventName) {
            case "extracting":
                log.info("eventName : {}", eventName);
                extract.process(json).subscribe();
                break;
            case "synthesizing":
                log.info("eventName : {}", eventName);
                synthesize.process(json).subscribe();
                break;
            case "decomposing":
                log.info("eventName : {}", eventName);
                decompose.process(json).subscribe();
                break;
        }
        return Mono.empty();
    }
}

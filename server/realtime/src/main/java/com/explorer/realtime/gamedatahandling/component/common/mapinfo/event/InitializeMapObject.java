package com.explorer.realtime.gamedatahandling.component.common.mapinfo.event;

import com.explorer.realtime.gamedatahandling.component.common.mapinfo.repository.MapObjectRepository;
import com.explorer.realtime.global.common.dto.Message;
import com.explorer.realtime.global.common.enums.CastingType;
import com.explorer.realtime.global.component.broadcasting.Broadcasting;
import com.explorer.realtime.global.util.MessageConverter;
import com.explorer.realtime.initializing.repository.MapRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class InitializeMapObject {

    private final MapRepository mapRepository;
    private final MapObjectRepository mapObjectRepository;
    private final Broadcasting broadcasting;


    public Mono<Void> initializeMapObject(String channelId, Integer mapId) {

        String itemCategory = "debris";       //
        Integer itemId = mapId - 1;
        Integer spaceSheep = 0;
        log.info("Initializing Map Object for channelId: {}, mapId: {}", channelId, mapId);

        return mapRepository.findMapData(mapId)
                .collectList()
                .flatMap(data -> {
                    List<String> selectedData = selectRandomEntries(data, 150);
                    List<String> selectSpaceSheep = selectRandomEntries(data, 1);
                    mapObjectRepository.saveMapData(channelId, mapId, selectedData, itemCategory, itemId).subscribe();
                    return mapObjectRepository.saveMapData(channelId, mapId, selectSpaceSheep, itemCategory, spaceSheep);
                })
                .flatMap(result -> {
                    if (result) {
                        return mapObjectRepository.findMapData(channelId, mapId)
                                .then();
                    } else {
                        log.error("Failed to save map data for channelId: {}, mapId: {}", channelId, mapId);
                        return Mono.error(new RuntimeException("Failed to save map data"));
                    }
                });
    }

    private List<String> selectRandomEntries(List<String> positions, int count) {
        List<String> selectedEntries = new ArrayList<>(positions);
        Collections.shuffle(selectedEntries);  // 리스트를 무작위로 섞음
        return selectedEntries.subList(0, Math.min(selectedEntries.size(), count));  // 랜덤하게 count개의 요소를 선택
    }
}

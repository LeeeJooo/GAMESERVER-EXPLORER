package com.explorer.realtime.channeldatahandling.service;

import com.explorer.realtime.channeldatahandling.dto.ChannelDetailsInfo;
import com.explorer.realtime.channeldatahandling.dto.ChannelInfo;
import com.explorer.realtime.sessionhandling.ingame.document.Channel;
import com.explorer.realtime.sessionhandling.ingame.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChannelService {

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public Mono<List<ChannelInfo>> findAllChannelInfoByUserId(Long userId) {
        ProjectionOperation project = Aggregation.project()
                .andExpression("_id").as("channelId")
                .andExpression("name").as("channelName");

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("playerList").is(userId)),
                project
        );

        return reactiveMongoTemplate.aggregate(aggregation, "channels", ChannelInfo.class)
                .collectList();
    }

    public Mono<ChannelDetailsInfo> findChannelDetailsInfoByChannelId(String channelId) {
        Query query = new Query(Criteria.where("_id").is(channelId));

        Mono<Channel> channelMono = reactiveMongoTemplate.findOne(query, Channel.class);

        Mono<Integer> playerListSizeMono = channelMono
                .map(channel -> channel.getPlayerList().size());

        Mono<LocalDateTime> createdAtMono = channelMono
                .map(Channel::getCreatedAt);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

        return Mono.zip(playerListSizeMono, createdAtMono)
                .map(tuple -> ChannelDetailsInfo.of(tuple.getT1(), tuple.getT2().format(formatter)));
    }

    public Flux<UserInfo> findPlayerListByChannelId(String channelId) {
        Query query = new Query(Criteria.where("_id").is(channelId));
        return reactiveMongoTemplate.findOne(query, Channel.class)
                .flatMapMany(channel -> Flux.fromIterable(channel.getPlayerList()));
    }

}

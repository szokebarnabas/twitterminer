package com.bs.twitterminer.analytics.domain;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class HashTagRepository {

    private Map<String, UserHashTags> hashTagMap = Maps.newConcurrentMap();

    public void addClient(String clientId) {
        hashTagMap.put(clientId, new UserHashTags());
    }

    public void removeClient(String clientId) {
        hashTagMap.remove(clientId);
    }

    public void addHashTagStat(String clientId, Map<String, Long> hashTags) {
        if (hashTagMap.containsKey(clientId)) {
            UserHashTags userHashTags = hashTagMap.get(clientId);
            userHashTags.sumHashTags(hashTags);
            log.info("HashTags has been accumulated");
        } else {
            log.warn("Client id not found: {}", clientId);
        }
    }

    public Map<String, UserHashTags> getHashTagMap() {
        return hashTagMap;
    }
}

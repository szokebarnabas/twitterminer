package com.bs.twitterminer.analytics.domain;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class HashTags {

    private Map<String, UserHashTags> clients = Maps.newConcurrentMap();

    public void addClient(String clientId) {
        clients.put(clientId, new UserHashTags());
    }

    public void removeClient(String clientId) {
        clients.remove(clientId);
    }

    public void addHashTagStat(String clientId, Map<String, Long> hashTags) {
        if (clients.containsKey(clientId)) {
            UserHashTags userHashTags = clients.get(clientId);
            userHashTags.sumHashTags(hashTags);
            log.info("HashTags has been accumulated");
        } else {
            log.warn("Client id not found: {}", clientId);
        }
    }
}

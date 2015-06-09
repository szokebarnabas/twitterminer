package com.bs.twitterminer.analytics.domain;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

@Component
@Slf4j
public class HashTagRepository {

    private Map<Client, UserHashTags> hashTagMap = Maps.newConcurrentMap();

    public synchronized void addClient(String sessionId, String clientId) {
        if (!containsClientId(clientId)) {
            Client client = new Client(sessionId, clientId);
            hashTagMap.put(client, new UserHashTags());
            log.info("Client {} has been added", clientId);
        } else {
            log.warn("Client {} is already registered", clientId);
        }
    }

    private boolean containsClientId(String clientId) {
        Optional<Map.Entry<Client, UserHashTags>> entry = hashTagMap.entrySet().stream().filter(new Predicate<Map.Entry<Client, UserHashTags>>() {
            @Override
            public boolean test(Map.Entry<Client, UserHashTags> clientUserHashTagsEntry) {
                return clientUserHashTagsEntry.getKey().getClientId().equals(clientId);
            }
        }).findAny();
        return entry.isPresent();
    }

    public void removeClientBySessionId(String sessionId) {
        UserHashTags success = hashTagMap.remove(new Client(sessionId));
        if (success == null) {
            log.warn("Failed to remove client by sessionId {}", sessionId);
        } else {
            log.info("Client by sessionId has been removed: {}", sessionId);
        }
    }

    public void addHashTagStat(String clientId, Map<String, Long> hashTags) {
        Optional<Map.Entry<Client, UserHashTags>> entry = getUserHashTagsByClientId(clientId);
        if (entry.isPresent()) {
            UserHashTags userHashTags = entry.get().getValue();
            userHashTags.sumHashTags(hashTags);
        } else {
            log.warn("Client id not found: {}", clientId);
        }
    }

    public UserHashTags getHasTagStatByClient(String clientId) {
        Optional<Map.Entry<Client, UserHashTags>> optionalEntry = getUserHashTagsByClientId(clientId);

        UserHashTags result = null;
        if (optionalEntry.isPresent()) {
            result = optionalEntry.get().getValue();
        } else {
            log.warn("HashTag statistics by clientId {} is not found", clientId);
        }
        return result;
    }

    private Optional<Map.Entry<Client, UserHashTags>> getUserHashTagsByClientId(final String clientId) {
        return hashTagMap.entrySet().stream().filter(new Predicate<Map.Entry<Client, UserHashTags>>() {
                @Override
                public boolean test(Map.Entry<Client, UserHashTags> clientUserHashTagsEntry) {
                    return clientUserHashTagsEntry.getKey().getClientId().equals(clientId);
                }
            }).findFirst();
    }

    public Optional<Map.Entry<Client, UserHashTags>> hashTagEntry(String sessionId) {
        return hashTagMap.entrySet().stream().filter(new Predicate<Map.Entry<Client, UserHashTags>>() {
            @Override
            public boolean test(Map.Entry<Client, UserHashTags> clientUserHashTagsEntry) {
                return clientUserHashTagsEntry.getKey().getSessionId().equals(sessionId);
            }
        }).findAny();
    }
}

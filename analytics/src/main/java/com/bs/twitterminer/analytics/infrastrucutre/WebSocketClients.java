package com.bs.twitterminer.analytics.infrastrucutre;

import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class WebSocketClients {

    private Map<String, HashTagStats> clients = Maps.newHashMap();

    public void addClient(String clientId) {
        clients.put(clientId, new HashTagStats());
    }

    public void removeClient(String clientId) {
        clients.remove(clientId);
    }
}

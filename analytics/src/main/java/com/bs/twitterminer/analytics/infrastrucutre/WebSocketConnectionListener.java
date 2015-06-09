package com.bs.twitterminer.analytics.infrastrucutre;

import com.bs.twitterminer.analytics.domain.Client;
import com.bs.twitterminer.analytics.domain.HashTagRepository;
import com.bs.twitterminer.analytics.domain.UserHashTags;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class WebSocketConnectionListener implements ApplicationListener<ApplicationEvent> {

    private static final String CLIENT_ID = "client-id";
    private static final String SIMP_SESSION_ID = "simpSessionId";
    @Autowired
    private HashTagRepository hashTagRepository;

    @Autowired
    private StreamService streamService;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if(event instanceof SessionConnectEvent) {
            handleSessionConnected((SessionConnectEvent) event);
        } else if(event instanceof SessionDisconnectEvent) {
            handleSessionDisconnect((SessionDisconnectEvent) event);
        }
    }

    private void handleSessionConnected(SessionConnectEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        String clientId = headers.getNativeHeader(CLIENT_ID).get(0);
        String sessionId = (String)headers.getHeader(SIMP_SESSION_ID);
        log.info("Connect [clientId={}]", clientId);
        hashTagRepository.addClient(sessionId, clientId);

    }

    private void handleSessionDisconnect(SessionDisconnectEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        String sessionId = (String)headers.getHeader(SIMP_SESSION_ID);
        Optional<Map.Entry<Client, UserHashTags>> optional = hashTagRepository.hashTagEntry(sessionId);
        if (optional.isPresent()) {
            String clientId = optional.get().getKey().getClientId();
            log.info("Disconnect [clientId={}]", clientId);
            hashTagRepository.removeClientBySessionId(optional.get().getKey().getSessionId());
            streamService.sendStopStreamCommand(clientId);
        } else {
            log.warn("Cannot find client by session id: {}", sessionId);
        }
    }

}

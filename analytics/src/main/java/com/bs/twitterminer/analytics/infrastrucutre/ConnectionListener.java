package com.bs.twitterminer.analytics.infrastrucutre;

import com.bs.twitterminer.analytics.domain.AnalyticsService;
import com.bs.twitterminer.analytics.domain.HashTags;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Slf4j
@Component
public class ConnectionListener implements ApplicationListener<ApplicationEvent> {

    @Autowired
    private HashTags hashTags;

    @Autowired
    private AnalyticsService analyticsService;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if(event instanceof SessionConnectEvent) {
            handleSessionConnected((SessionConnectEvent) event);
        } else if(event instanceof SessionDisconnectEvent) {
            handleSessionDisconnect((SessionDisconnectEvent) event);
        }
    }

    private void handleSessionDisconnect(SessionDisconnectEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        String sessionId = headers.getSessionId();
        log.info("Disconnect [sessionId: " + sessionId + "]");
        hashTags.removeClient(sessionId);
        analyticsService.sendStopStreamCommand(sessionId);
    }

    private void handleSessionConnected(SessionConnectEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        String sessionId = headers.getSessionId();
        log.info("Connect [sessionId: " + sessionId + "]");
        hashTags.addClient(sessionId);

    }
}

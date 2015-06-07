package com.bs.twitterminer.analytics.infrastrucutre;

import com.bs.twitterminer.analytics.domain.HashTagRepository;
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
public class WebSocketConnectionListener implements ApplicationListener<ApplicationEvent> {

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

    private void handleSessionDisconnect(SessionDisconnectEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
//        String username = headers.getUser().getName();
        String sessionId = headers.getSessionId();
        log.info("Disconnect [sessionId={}]", sessionId);
        hashTagRepository.removeClient(sessionId);
        streamService.sendStopStreamCommand(sessionId);
    }

    private void handleSessionConnected(SessionConnectEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
//        String username = headers.getUser().getName();
        String sessionId = headers.getSessionId();
        log.info("Connect [sessionId={}]", sessionId);
        hashTagRepository.addClient(sessionId);

    }
}

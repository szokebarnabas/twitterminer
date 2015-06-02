package com.bs.twitterminer.analytics.infrastrucutre;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;

@Slf4j
public class PresenceChannelInterceptor extends ChannelInterceptorAdapter {

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {

        StompHeaderAccessor sha = StompHeaderAccessor.wrap(message);

        // ignore non-STOMP messages like heartbeat messages
        if(sha.getCommand() == null) {
            return;
        }

        String sessionId = sha.getSessionId();

        switch(sha.getCommand()) {
            case CONNECT:
                log.info("STOMP Connect [sessionId: " + sessionId + "]");
                break;
            case CONNECTED:
                log.info("STOMP Connected [sessionId: " + sessionId + "]");
                break;
            case DISCONNECT:
                log.info("STOMP Disconnect [sessionId: " + sessionId + "]");
                break;
            default:
                break;

        }
    }
}

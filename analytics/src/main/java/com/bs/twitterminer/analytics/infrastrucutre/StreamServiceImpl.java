package com.bs.twitterminer.analytics.infrastrucutre;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.bs.messaging.MessagingService;
import org.bs.messaging.StartStreamCommand;
import org.bs.messaging.StopStreamCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class StreamServiceImpl implements StreamService {

    private static final String COMMANDS_QUEUE = "commands";

    @Autowired
    private MessagingService messagingService;

    @Override
    public void sendStartStreamCommand(String clientId, List<String> keywords) {
        Preconditions.checkNotNull(clientId);
        Preconditions.checkNotNull(keywords);
        log.info("Start endpoint has been called. clientId = {}, Keywords = {}", clientId, keywords);
        messagingService.send(COMMANDS_QUEUE, new StartStreamCommand(clientId, keywords));
    }

    @Override
    public void sendStopStreamCommand(String clientId) {
        Preconditions.checkNotNull(clientId);
        log.info("Stop endpoint has been called. clientId = {}", clientId);
        messagingService.send(COMMANDS_QUEUE, new StopStreamCommand(clientId));
    }
}

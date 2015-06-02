package com.bs.twitterminer.analytics.infrastrucutre;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StreamListener {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    private static final String TWEETS_QUEUE = "tweets";

    @Autowired
    private HashTagExtractor hashTagExtractor;
    @RabbitListener(queues = TWEETS_QUEUE)
    public void onNotification(Message<String> tweet) {
        log.info("Received: {}", tweet.getPayload());
        simpMessagingTemplate.convertAndSend("/topic/tweets", tweet.getPayload());
    }
}

package com.bs.twitterminer.analytics.infrastrucutre;

import com.bs.twitterminer.analytics.domain.HashTags;
import com.bs.twitterminer.analytics.domain.Tweet;
import lombok.extern.slf4j.Slf4j;
import org.bs.messaging.JsonMessageSerializer;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class StreamListener {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    private static final String TWEETS_QUEUE = "tweets";

    @Autowired
    private JsonMessageSerializer jsonMessageSerializer;

    @Autowired
    private HashTagExtractor hashTagExtractor;

    @Autowired
    private HashTags hashTags;

    @RabbitListener(queues = TWEETS_QUEUE)
    public void handleStreamMessage(Message<String> tweet) {
        String payload = tweet.getPayload();
        log.info("Received: {}", payload);
        Tweet command = jsonMessageSerializer.getObject(payload, Tweet.class);
        Map<String, Long> extractedHashTags = hashTagExtractor.extract(command.getText());
        hashTags.addHashTagStat(command.getStreamId(), extractedHashTags);
        simpMessagingTemplate.convertAndSend("/queue/tweets", payload);
    }
}

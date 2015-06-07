package com.bs.twitterminer.analytics.infrastrucutre;

import com.bs.twitterminer.analytics.domain.HashTagRepository;
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
    private HashTagRepository hashTagRepository;

    @RabbitListener(queues = TWEETS_QUEUE)
    public void handleStreamMessage(Message<String> tweet) {
        String payload = tweet.getPayload();
        log.info("Received: {}", payload);
        Tweet tweetMsg = jsonMessageSerializer.getObject(payload, Tweet.class);
        //TODO extract into an assembler
        Map<String, Long> extractedHashTags = hashTagExtractor.extract(tweetMsg.getText());
        hashTagRepository.addHashTagStat(tweetMsg.getStreamId(), extractedHashTags);

        simpMessagingTemplate.convertAndSend("/queue/tweets", payload);
//        simpMessagingTemplate.convertAndSend("/queue/statistics", payload);
    }


//    @RabbitListener(queues = TWEETS_QUEUE)
//    public void handleStreamMessage(Message<String> tweet) {
//        String payload = tweet.getPayload();
//        log.info("Received: {}", payload);
//        Tweet tweetMsg = jsonMessageSerializer.getObject(payload, Tweet.class);
//        String username = "barna";
//        simpMessagingTemplate.convertAndSend("/user/" + username + "/queue/tweet.message", tweetMsg);
//    }
}

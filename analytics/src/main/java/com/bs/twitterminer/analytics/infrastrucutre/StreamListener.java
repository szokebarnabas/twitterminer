package com.bs.twitterminer.analytics.infrastrucutre;

import com.bs.twitterminer.analytics.domain.HashTagRepository;
import com.bs.twitterminer.analytics.domain.Tweet;
import com.bs.twitterminer.analytics.domain.UserHashTags;
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

        simpMessagingTemplate.convertAndSend("/queue/tweets/" + tweetMsg.getClientId(), payload);

        if (!extractedHashTags.isEmpty()) {
            hashTagRepository.addHashTagStat(tweetMsg.getClientId(), extractedHashTags);
            UserHashTags hasTagStatByClient = hashTagRepository.getHasTagStatByClient(tweetMsg.getClientId());
            simpMessagingTemplate.convertAndSend("/queue/hashtagstats/" + tweetMsg.getClientId(), jsonMessageSerializer.getJson(hasTagStatByClient));
        }

    }
}

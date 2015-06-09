package com.bs.twitterminer.analytics.infrastrucutre;

import com.bs.twitterminer.analytics.domain.StatisticsRepository;
import com.bs.twitterminer.analytics.domain.Tweet;
import com.bs.twitterminer.analytics.domain.UserHashTags;
import lombok.extern.slf4j.Slf4j;
import org.bs.messaging.JsonMessageSerializer;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class StreamListener {

    private static final String QUEUE_TWEETS = "/queue/tweets/";
    private static final String QUEUE_HASHTAGSTATS = "/queue/hashtagstats/";
    private static final String QUEUE_TWEETSTATS = "/queue/tweetstats/";
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    private static final String TWEETS_QUEUE = "tweets";

    @Autowired
    private JsonMessageSerializer jsonMessageSerializer;

    @Autowired
    private HashTagExtractor hashTagExtractor;

    @Autowired
    private KeywordStatistics keywordStatistics;

    @Autowired
    private StatisticsRepository statisticsRepository;

    @RabbitListener(queues = TWEETS_QUEUE)
    public void handleStreamMessage(Message<String> tweet) {
        String payload = tweet.getPayload();
        log.info("Received: {}", payload);
        Tweet tweetMsg = jsonMessageSerializer.getObject(payload, Tweet.class);
        //TODO extract into an assembler
        Map<String, Long> extractedHashTags = hashTagExtractor.extract(tweetMsg.getText());
        Map<String, Long> tweetOccurrances = keywordStatistics.extract(tweetMsg.getText(), tweetMsg.getKeywords());

        simpMessagingTemplate.convertAndSend(QUEUE_TWEETS + tweetMsg.getClientId(), payload);

        if (!extractedHashTags.isEmpty()) {
            statisticsRepository.addHashTagStat(tweetMsg.getClientId(), extractedHashTags);
            UserHashTags hasTagStatByClient = statisticsRepository.getHasTagStatByClient(tweetMsg.getClientId());
            simpMessagingTemplate.convertAndSend(QUEUE_HASHTAGSTATS + tweetMsg.getClientId(), jsonMessageSerializer.getJson(hasTagStatByClient));
        }

        statisticsRepository.addTweetStat(tweetMsg.getClientId(), tweetOccurrances);
        Map<String, Long> tweetStatByClient = statisticsRepository.getTweetStatByClient(tweetMsg.getClientId());
        simpMessagingTemplate.convertAndSend(QUEUE_TWEETSTATS + tweetMsg.getClientId(), tweetStatByClient);

    }
}

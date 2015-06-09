package com.bs.twitterminer.stream.infrastructure.primary;

import com.bs.twitterminer.stream.app.TweetAssembler;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.bs.messaging.MessagingService;
import org.bs.messaging.Tweet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.FilterQuery;
import twitter4j.Status;
import twitter4j.StatusAdapter;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class TwitterStreamServiceImpl implements StreamService {

    @Autowired
    private MessagingService messagingService;

    private Map<String, TwitterStream> streamInstances = new HashMap<>();

    @Autowired
    private TweetAssembler tweetAssembler;

    private static final String TWEETS_QUEUE = "tweets";

    @Override
    public void createStream(String clientId, List<String> keywords) {
        TwitterStream stream;
        if (streamInstances.containsKey(clientId)) {
            stream = streamInstances.get(clientId);
        } else {
            log.info("Stream {} has been created", clientId);
            stream = new TwitterStreamFactory().getInstance();
            stream.addListener(new StatusAdapter() {
                @Override
                public void onStatus(Status status) {
                    String profileImageUrl = status.getUser().getProfileImageURL();
                    Tweet tweet = tweetAssembler.createTweet(status.getId(), status.getUser().getName(), status.getCreatedAt(), status.getText(), clientId, profileImageUrl, keywords);
                    messagingService.send(TWEETS_QUEUE, tweet);
                }
            });
            streamInstances.put(clientId, stream);
        }
        FilterQuery filterQuery = new FilterQuery();
        filterQuery.track(keywords.toArray(new String[]{}));
        filterQuery.language(new String[]{"en"});
        stream.filter(filterQuery);
        log.info("Filter criteria of stream {}: {}", clientId, keywords);
    }

    @Override
    public void deleteStream(String clientId) {
        Preconditions.checkNotNull(clientId);
        if (streamInstances.containsKey(clientId)) {
            TwitterStream stream = streamInstances.remove(clientId);
            stream.cleanUp();
            log.info("Stream {} has been deleted.", clientId);
        } else {
            log.warn("Stream {} has not been found.", clientId);
        }
    }
}

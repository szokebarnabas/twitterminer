package com.bs.twitterminer.stream.app;

import org.bs.messaging.Tweet;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class TweetAssembler {

    public Tweet createTweet(Long statusId, String userName, Date createdAt, String text, String clientId, String profileImageUrl, List<String> keywords) {
        return new Tweet(statusId, userName, createdAt, text, clientId, profileImageUrl, keywords);
    }
}

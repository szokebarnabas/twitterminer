package com.bs.twitterminer.stream.app;

import org.bs.messaging.Tweet;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TweetAssembler {

    public Tweet createTweet(Long statusId, String userName, Date createdAt, String text, String streamId) {
        return new Tweet(statusId, userName, createdAt, text, streamId);
    }
}

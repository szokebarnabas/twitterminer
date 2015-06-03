package com.bs.twitterminer.analytics.domain;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;

import java.util.Map;
import java.util.function.BiConsumer;

public class UserHashTags {

    private Map<String, Long> summarizedHashTags = Maps.newHashMap();

    public void sumHashTags(Map<String, Long> hashTagStat) {
        hashTagStat.forEach(new BiConsumer<String, Long>() {
            @Override
            public void accept(String hashTag, Long occurrence) {
                if (summarizedHashTags.containsKey(hashTag)) {
                    long sum = summarizedHashTags.get(hashTag) + occurrence;
                    summarizedHashTags.put(hashTag, sum);
                } else {
                    summarizedHashTags.put(hashTag, occurrence);
                }
            }
        });
    }

    public Map<String, Long> getSummarizedHashTags() {
        return summarizedHashTags;
    }

    @VisibleForTesting
    void setSummarizedHashTags(Map<String, Long> summarizedHashTags) {
        this.summarizedHashTags = summarizedHashTags;
    }
}

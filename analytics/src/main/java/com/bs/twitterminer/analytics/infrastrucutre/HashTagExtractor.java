package com.bs.twitterminer.analytics.infrastrucutre;

import com.google.common.base.Splitter;
import org.springframework.stereotype.Component;

import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

@Component
public class HashTagExtractor {

    public Map<String, Long> extract(String tweet) {
        Map<String, Long> tokens = Splitter.on(" ").splitToList(tweet).stream()
                .filter(token -> token.startsWith("#"))
                .collect(groupingBy(identity(), counting()));
        return tokens;
    }
}

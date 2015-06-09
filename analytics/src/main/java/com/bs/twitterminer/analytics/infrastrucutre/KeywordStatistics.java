package com.bs.twitterminer.analytics.infrastrucutre;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Component
public class KeywordStatistics {

    public Map<String, Long> extract(String text, List<String> keywords) {
        Map<String, Long> result = Maps.newHashMap();
        keywords.forEach(new Consumer<String>() {
            @Override
            public void accept(String keyword) {
                if (text.contains(keyword)) {
                    result.put(keyword, 1L);
                }
            }
        });

        return result;
    }
}

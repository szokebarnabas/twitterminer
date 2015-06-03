package com.bs.twitterminer.analytics.infrastrucutre;

import com.bs.twitterminer.analytics.domain.SearchCriteria;
import com.bs.twitterminer.analytics.domain.AnalyticsService;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class WebsocketListener {

    public static final String SIMP_SESSION_ID = "simpSessionId";
    @Autowired
    private AnalyticsService analyticsService;

    @MessageMapping("/search")
    public void receive(@Header(value = SIMP_SESSION_ID) String sessionId, SearchCriteria searchCriteria) throws Exception {
        log.info("Received websocket message: {}", searchCriteria);
        Preconditions.checkNotNull(searchCriteria);
        Preconditions.checkNotNull(searchCriteria.getCriteria());
        List<String> keywordTokens = new ArrayList<>(StringUtils.commaDelimitedListToSet(searchCriteria.getCriteria()));
        analyticsService.sendStartStreamCommand(sessionId, keywordTokens);
    }
}
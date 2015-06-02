package com.bs.twitterminer.analytics.infrastrucutre;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.bs.messaging.MessagingService;
import org.bs.messaging.StartStreamCommand;
import org.bs.messaging.StopStreamCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

//@RestController
//@RequestMapping("/analytics")
@Slf4j
class AnalyticsController {

    private static final String COMMANDS_QUEUE = "commands";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MessagingService messagingService;

    @RequestMapping(value = "/start/{streamId}", method = RequestMethod.GET)
    public void sendStartStreamCommand(@PathVariable String streamId, @RequestParam(value="keywords") String keywords) {
        Preconditions.checkNotNull(streamId);
        Preconditions.checkNotNull(keywords);
        List<String> keywordTokens = new ArrayList<>(StringUtils.commaDelimitedListToSet(keywords));
        log.info("Start endpoint has been called. UserId = {}, Keywords = {}", streamId, keywordTokens);
        messagingService.send(COMMANDS_QUEUE, new StartStreamCommand(streamId, keywordTokens));

//        ResponseEntity<String> exchange = this.restTemplate.exchange(
//                "http://stream-service/stream/startStream",
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<String>() {
//                },
//                (Object) "mstine");
//
//        String body = exchange.getBody();


    }

    @RequestMapping(value = "/stop/{streamId}", method = RequestMethod.GET)
    public void sendStopStreamCommand(@PathVariable String streamId) {
        Preconditions.checkNotNull(streamId);
        log.info("Stop endpoint has been called. UserId = {}", streamId);
        messagingService.send(COMMANDS_QUEUE, new StopStreamCommand(streamId));
    }
}
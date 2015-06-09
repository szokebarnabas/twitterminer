package com.bs.twitterminer.analytics.infrastrucutre;

import com.bs.twitterminer.analytics.domain.SearchCriteria;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class WebsocketListener {

    private static final String CLIENT_ID = "client-id";
    @Autowired
    private StreamService streamService;

    @MessageMapping("/search")
    public String receive(@Header(value = CLIENT_ID) String clientId, SearchCriteria searchCriteria) throws Exception {
        log.info("Received websocket message: {} from client {}", searchCriteria, clientId);
        Preconditions.checkNotNull(searchCriteria);
        Preconditions.checkNotNull(searchCriteria.getCriteria());
        //TODO refactor to an assembler
        List<String> keywordTokens = new ArrayList<>(StringUtils.commaDelimitedListToSet(searchCriteria.getCriteria()));
        streamService.sendStartStreamCommand(clientId, keywordTokens);
        return clientId;
    }
}
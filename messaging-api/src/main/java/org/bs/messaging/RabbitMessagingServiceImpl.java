package org.bs.messaging;

import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Component
public class RabbitMessagingServiceImpl implements MessagingService {

    @Autowired
    private RabbitMessagingTemplate messagingTemplate;

    @Autowired
    private JsonMessageSerializer messageSerializer;

    private static final String MESSAGE_TYPE = "MessageType";

    @Override
    public <T extends Serializable> void send(String queue, T data) {
        Map<String, Object> header = new HashMap<>();
        header.put(MESSAGE_TYPE, data.getClass().getName());
        String json = messageSerializer.getJson(data);
        this.messagingTemplate.convertAndSend(queue, (Object)json, header);
    }

}

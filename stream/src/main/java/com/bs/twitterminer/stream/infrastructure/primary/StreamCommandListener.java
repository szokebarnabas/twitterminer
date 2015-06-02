package com.bs.twitterminer.stream.infrastructure.primary;

import lombok.extern.slf4j.Slf4j;
import org.bs.messaging.JsonMessageSerializer;
import org.bs.messaging.StartStreamCommand;
import org.bs.messaging.StopStreamCommand;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StreamCommandListener {

    private static final String COMMANDS_QUEUE = "commands";
    private static final String MESSAGE_TYPE = "MessageType";

    @Autowired
    private StreamService streamService;

    @Autowired
    private JsonMessageSerializer jsonMessageSerializer;


    @RabbitListener(queues = COMMANDS_QUEUE)
    public void onMessage(Message<String> message) {
        log.info("Stream command has been received: {}", message);
        String messageType = (String)message.getHeaders().get(MESSAGE_TYPE);
        if (messageType.equals(StartStreamCommand.class.getName())) {
            StartStreamCommand command = jsonMessageSerializer.getObject(message.getPayload(), StartStreamCommand.class);
            streamService.createStream(command.getStreamId(), command.getKeywords());
        } else if (messageType.equals(StopStreamCommand.class.getName())) {
            StopStreamCommand command = jsonMessageSerializer.getObject(message.getPayload(), StopStreamCommand.class);
            streamService.deleteStream(command.getStreamId());
        }
    }

}

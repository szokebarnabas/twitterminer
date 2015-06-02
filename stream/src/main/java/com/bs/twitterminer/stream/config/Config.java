package com.bs.twitterminer.stream.config;

import org.bs.messaging.JsonMessageSerializer;
import org.bs.messaging.MessagingService;
import org.bs.messaging.RabbitMessagingServiceImpl;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class Config {

    private static final String COMMANDS_QUEUE = "commands";

    @Bean
    public JsonMessageSerializer messageSerializer() {
        return new JsonMessageSerializer();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public MessagingService messagingService() {
        return new RabbitMessagingServiceImpl();
    }


    @Bean
    public InitializingBean prepareQueues(AmqpAdmin amqpAdmin) {
        return () -> {
            org.springframework.amqp.core.Queue queue = new org.springframework.amqp.core.Queue(COMMANDS_QUEUE, true);
            DirectExchange exchange = new DirectExchange(COMMANDS_QUEUE);
            Binding binding = BindingBuilder.bind(queue).to(exchange)
                    .with(COMMANDS_QUEUE);
            amqpAdmin.declareQueue(queue);
            amqpAdmin.declareExchange(exchange);
            amqpAdmin.declareBinding(binding);
        };
    }
}
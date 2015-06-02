package com.bs.twitterminer.analytics.config;

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
public class MessageConfig {

    private static final String TWEETS_QUEUE = "tweets";

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
            org.springframework.amqp.core.Queue queue = new org.springframework.amqp.core.Queue(TWEETS_QUEUE, true);
            DirectExchange exchange = new DirectExchange(TWEETS_QUEUE);
            Binding binding = BindingBuilder.bind(queue).to(exchange)
                    .with(TWEETS_QUEUE);
            amqpAdmin.declareQueue(queue);
            amqpAdmin.declareExchange(exchange);
            amqpAdmin.declareBinding(binding);
        };
    }


}
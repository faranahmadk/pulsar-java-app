package org.example.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.*;
import org.example.consumer.ProductConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class PulsarConfig {
    private static final String SUBSCRIPTION_NAME = "my-subscription";

    @Autowired
    ProductConsumer productConsumer;

    @Value("${pulsar.service-url}")
    private String pulsarServiceUrl;

    @Value("${pulsar.topic-name}")
    private String topicName;

    @Bean
    public PulsarClient pulsarClient() throws PulsarClientException {
        log.info("Connecting to broker...");
        return PulsarClient
            .builder()
            .serviceUrl(pulsarServiceUrl)
            .build();
    }

    @Bean
    public Consumer<String> foo(final PulsarClient pulsarClient) throws PulsarClientException {
        log.info("Starting foo consumer...");
        return pulsarClient
            .newConsumer(Schema.STRING)
            .topic(topicName)
            .subscriptionName(SUBSCRIPTION_NAME)
            .subscriptionType(SubscriptionType.Shared)
            .messageListener(productConsumer)
            .consumerName("foo")
            .subscribe();
    }

    @Bean
    public Consumer<String> bar(final PulsarClient pulsarClient) throws PulsarClientException {
        log.info("Starting bar consumer...");
        return pulsarClient
            .newConsumer(Schema.STRING)
            .topic(topicName)
            .subscriptionName(SUBSCRIPTION_NAME)
            .subscriptionType(SubscriptionType.Shared)
            .messageListener(productConsumer)
            .consumerName("bar")
            .subscribe();
    }
}

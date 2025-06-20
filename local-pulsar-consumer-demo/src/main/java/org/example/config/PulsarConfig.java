package org.example.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.*;
import org.example.consumer.ProductConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.io.IOException;

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

    @EventListener
    public void onApplicationEvent(final ApplicationReadyEvent event) throws InterruptedException, IOException {
        boolean consumerOneConnected = false;
        boolean consumerTwoConnected = false;

        final PulsarClient pulsarClient = pulsarClient();

        while (!(consumerOneConnected && consumerTwoConnected)) {
            if (!consumerOneConnected) {
                try {
                    initializeConsumerOne(pulsarClient);
                    consumerOneConnected = true;
                } catch (PulsarClientException e) {
                    log.warn("Consumer one is not ready yet");
                }
            }

            if (!consumerTwoConnected) {
                try {
                    initializeConsumerTwo(pulsarClient);
                    consumerTwoConnected = true;
                } catch (PulsarClientException e) {
                    log.warn("Consumer two is not ready yet");
                }
            }

            if (!(consumerOneConnected && consumerTwoConnected)) {
                log.info("Sleeping for 10 minutes.");
                Thread.sleep(600000);
            }
        }
    }

    @Bean
    public PulsarClient pulsarClient() throws PulsarClientException {
        return PulsarClient
            .builder()
            .serviceUrl(pulsarServiceUrl)
            .build();
    }

    @Bean
    public Consumer<String> initializeConsumerOne(final PulsarClient pulsarClient) throws PulsarClientException {
        return pulsarClient
            .newConsumer(Schema.STRING)
            .topic(topicName)
            .subscriptionName(SUBSCRIPTION_NAME)
            .subscriptionType(SubscriptionType.Shared)
            .messageListener(productConsumer)
            .consumerName("Consumer 1")
            .subscribe();
    }

    @Bean
    public Consumer<String> initializeConsumerTwo(final PulsarClient pulsarClient) throws PulsarClientException {
        return pulsarClient
            .newConsumer(Schema.STRING)
            .topic(topicName)
            .subscriptionName(SUBSCRIPTION_NAME)
            .subscriptionType(SubscriptionType.Shared)
            .messageListener(productConsumer)
            .consumerName("Consumer 2")
            .subscribe();
    }
}

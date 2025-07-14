package org.example.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class PulsarConfig {

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
    public Producer<String> pulsarProducer(final PulsarClient pulsarClient) throws PulsarClientException {
        log.info("Creating producer...");
        return pulsarClient
            .newProducer(Schema.STRING)
            .topic(topicName)
            .producerName("product-event-producer")
            .create();
    }
}

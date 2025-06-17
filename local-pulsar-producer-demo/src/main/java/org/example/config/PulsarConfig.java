package org.example.config;

import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PulsarConfig {

    @Value("${pulsar.service-url}")
    private String pulsarServiceUrl;

    @Value("${pulsar.topic-name}")
    private String topicName;


    @Bean
    public PulsarClient pulsarClient() throws PulsarClientException {
        return PulsarClient
            .builder()
            .serviceUrl(pulsarServiceUrl)
            .build();
    }

    @Bean
    public Producer<String> pulsarProducer(final PulsarClient pulsarClient) throws PulsarClientException {
        return pulsarClient
            .newProducer(Schema.STRING)
            .topic(topicName)
            .create();
    }
}

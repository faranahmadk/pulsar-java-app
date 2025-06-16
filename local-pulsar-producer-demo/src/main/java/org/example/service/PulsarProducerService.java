package org.example.service;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PulsarProducerService {

    private final Producer<String> producer;


    @Autowired
    public PulsarProducerService(PulsarClient pulsarClient) throws PulsarClientException {
        this.producer = pulsarClient
            .newProducer(Schema.STRING)
            .topic("persistent://public/default/test-topics")
            .create();
    }

    public void sendMessage(String message) throws PulsarClientException {
        for (int i = 0; i < 1000; i++) {
            producer.send(message);
            log.info("Message number {} sent: {}", i, message);
        }
    }
    
    @PreDestroy
    public void cleanUp() {
        try {
            producer.close();
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
    }
}

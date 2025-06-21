package org.example.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClientException;
import org.example.dto.ProductEvent;
import org.example.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("pulsarProducer")
@Slf4j
public class PulsarProducerServiceImpl implements ProducerService {

    @Autowired
    private Producer<String> pulsarProducer;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void forward(final ProductEvent productEvent) {
        try {
            for (int i = 0; i < 1000; i++) {
                pulsarProducer.newMessage()
                    .value(objectMapper.writeValueAsString(productEvent))
                    .send();
                log.info("msg# {}, msg: {}", i, productEvent);
            }
        } catch (PulsarClientException | JsonProcessingException e) {
            log.error("Error while writing to pulsar topic", e);
        }
    }

    @PreDestroy
    public void cleanUp() {
        try {
            pulsarProducer.close();
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
    }
}

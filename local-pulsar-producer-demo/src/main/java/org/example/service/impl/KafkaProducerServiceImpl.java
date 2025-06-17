package org.example.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.ProductEvent;
import org.example.service.ProducerService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("kafkaProducer")
@Slf4j
public class KafkaProducerServiceImpl implements ProducerService {

    @Override
    public void forward(final ProductEvent productEvent) {
        log.info("message produced to kafka topic: {}", productEvent);
    }
}

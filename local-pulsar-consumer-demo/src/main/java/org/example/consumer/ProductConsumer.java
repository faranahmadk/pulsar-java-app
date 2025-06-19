package org.example.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.*;
import org.example.dto.ProductEvent;
import org.example.service.ProductEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class ProductConsumer implements MessageListener<String> {

    @Autowired
    MeterRegistry metrics;

    @Autowired
    private ProductEventService productEventService;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public void received(final Consumer<String> consumer, final Message<String> message) {
        metrics.counter("order-event.consumer").increment();
        final String messageData = new String(message.getData(), StandardCharsets.UTF_8);
        log.debug("Received pulsar event: {}", messageData);

        try {
            final ProductEvent productEvent = mapper.readValue(messageData, ProductEvent.class);

            if (productEvent != null) {
                productEventService.handleMessage(productEvent);
            }
        } catch (final JsonProcessingException e) {
            metrics.counter("order-event.consumer.failure." + e.getClass().getSimpleName()).increment();
            log.error("Failed to handle product event: {}", messageData, e);
        }

        final MessageId messageId = message.getMessageId();

        try {
            metrics.counter("order-event.consumer.success").increment();
            consumer.acknowledge(messageId);
        } catch (final PulsarClientException e) {
            metrics.counter("order-event.consumer.pulsar-exception").increment();
            consumer.negativeAcknowledge(message);
            log.error("Error acknowledging message: {}", messageData, e);
        }
        log.info("Consumer: {} {}", consumer.getConsumerName(), consumer.getStats().getNumAcksSent());
    }
}

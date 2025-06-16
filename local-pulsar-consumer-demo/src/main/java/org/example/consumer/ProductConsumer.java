package org.example.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.MessageListener;
import org.example.dto.ProductEvent;
import org.example.service.ProductEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class ProductConsumer implements MessageListener<String> {

    @Autowired
    private ProductEventService productEventService;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public void received(final Consumer<String> consumer, final Message<String> message) {
        final String messageData = new String(message.getData(), StandardCharsets.UTF_8);
        log.debug("Received pulsar event: {}", messageData);

        try {
            final ProductEvent productEvent = mapper.readValue(messageData, ProductEvent.class);

            if (productEvent != null) {
                productEventService.handleMessage(productEvent);
            }

        } catch (final Exception e) {
            log.error("Failed to handle product event: {}", messageData, e);
        }

        final MessageId messageId = message.getMessageId();

        try {
            consumer.acknowledge(messageId);
        } catch (Exception e) {
            consumer.negativeAcknowledge(message);
            log.error("Error acknowledging message: {}", messageData, e);
        }

        log.info("Consumer: {} {}", consumer.getConsumerName(), consumer.getStats().getNumAcksSent());
    }
}

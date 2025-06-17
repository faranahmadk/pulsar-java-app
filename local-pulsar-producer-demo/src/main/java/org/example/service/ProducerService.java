package org.example.service;

import org.example.dto.ProductEvent;

public interface ProducerService {
    /**
     * produce message to topic.
     *
     * @param productEvent
     */
    void forward(ProductEvent productEvent);
}

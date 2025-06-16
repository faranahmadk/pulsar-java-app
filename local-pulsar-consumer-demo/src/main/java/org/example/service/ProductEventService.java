package org.example.service;

import org.example.dto.ProductEvent;

public interface ProductEventService {
    void handleMessage(ProductEvent productEvent);
}


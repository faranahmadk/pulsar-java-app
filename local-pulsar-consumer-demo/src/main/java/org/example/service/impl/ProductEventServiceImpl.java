package org.example.service.impl;

import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.ProductEvent;
import org.example.service.ProductEventService;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class ProductEventServiceImpl implements ProductEventService {

    @Override
    public void handleMessage(final ProductEvent productEvent) {
        if (!validProductEvent(productEvent)) {
            log.debug("Invalid message received for ProductEventService. productId: {}, productName: {}", productEvent.productId(), productEvent.productName());
        }
        // do something
        log.info("Product Name: {}", productEvent.productName());
    }

    private boolean validProductEvent(ProductEvent productEvent) {
        return StringUtils.isNotBlank(productEvent.productName()) && productEvent.productId() != null;
    }
}

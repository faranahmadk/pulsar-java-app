package org.example.dto;

import lombok.Builder;

@Builder
public record ProductEvent(Long productId, String productName) {
}

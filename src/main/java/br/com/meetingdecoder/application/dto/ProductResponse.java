package br.com.meetingdecoder.application.dto;

import br.com.meetingdecoder.domain.enums.ServiceCategory;
import br.com.meetingdecoder.domain.model.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ProductResponse(
        UUID id,
        String name,
        ServiceCategory category,
        String description,
        BigDecimal price
) {
    public static ProductResponse from(
            Product payload
    ) {
        return new ProductResponse(
                payload.getId(),
                payload.getName(),
                payload.getCategory(),
                payload.getDescription(),
                payload.getPrice()
        );
    }

    public static List<ProductResponse> from(
            List<Product> list
    ) {
        return list.stream()
                .map(ProductResponse::from)
                .toList();
    }
}

package br.com.meetingdecoder.application.dto;

import br.com.meetingdecoder.domain.enums.ServiceCategory;
import br.com.meetingdecoder.domain.model.ServiceRecommendation;

import java.math.BigDecimal;
import java.util.UUID;

public record ServiceRecommendationResponse(
        UUID id,
        String name,
        ServiceCategory category,
        String description,
        BigDecimal price
) {
    public static ServiceRecommendationResponse from(
            ServiceRecommendation payload
    ) {
        return new ServiceRecommendationResponse(
                payload.getId(),
                payload.getName(),
                payload.getCategory(),
                payload.getDescription(),
                payload.getPrice()
        );
    }
}

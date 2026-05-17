package br.com.meetingdecoder.application.command.serviceRecommendation;

import br.com.meetingdecoder.domain.enums.ServiceCategory;

import java.math.BigDecimal;

public record CreateServiceRecommendationCommand(
        String name,
        ServiceCategory category,
        String description,
        BigDecimal price
) {
}

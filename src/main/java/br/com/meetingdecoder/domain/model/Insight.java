package br.com.meetingdecoder.domain.model;

import br.com.meetingdecoder.domain.validation.DomainValidation;

import java.util.UUID;

public class Insight {
    private UUID id;
    private String message;
    private UUID transcriptionId;
    private UUID serviceRecommendationId;

    private Insight(
            String message,
            UUID transcriptionId,
            UUID serviceRecommendationId
    ) {
        validate(message, transcriptionId, serviceRecommendationId);

        this.id = UUID.randomUUID();
        this.message = message;
        this.transcriptionId = transcriptionId;
        this.serviceRecommendationId = serviceRecommendationId;
    }

    private void validate(String message,  UUID transcriptionId, UUID serviceRecommendationId) {
        DomainValidation.notBlank(message, "message");
        DomainValidation.notNull(transcriptionId, "transcriptionId");
        DomainValidation.notNull(serviceRecommendationId, "serviceRecommendationId");
    }

    public static Insight create(
            String message,
            UUID transcriptionId,
            UUID serviceRecommendationId
    ) {
        return new Insight(message, transcriptionId, serviceRecommendationId);
    }
}

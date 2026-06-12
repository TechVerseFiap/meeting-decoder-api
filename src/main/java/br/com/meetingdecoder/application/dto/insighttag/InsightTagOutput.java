package br.com.meetingdecoder.application.dto.insighttag;

import br.com.meetingdecoder.domain.insight.model.InsightTag;

import java.time.Instant;
import java.util.UUID;

public record InsightTagOutput(
        UUID id,
        String nome,
        Instant createdAt
) {

    public static InsightTagOutput from(
            InsightTag tag
    ) {

        return new InsightTagOutput(
                tag.getId().value(),
                tag.getNome(),
                tag.getCreatedAt()
        );
    }
}

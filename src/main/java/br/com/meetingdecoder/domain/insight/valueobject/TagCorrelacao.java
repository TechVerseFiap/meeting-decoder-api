package br.com.meetingdecoder.domain.insight.valueobject;

import java.time.Instant;

public record TagCorrelacao(
        InsightTagId tagId,
        ScoreConfiabilidade score,
        Instant createdAt
) {

    public TagCorrelacao {
        if (tagId == null) {
            throw new IllegalArgumentException("tagId cannot be null");
        }

        if (score == null) {
            throw new IllegalArgumentException("score cannot be null");
        }

        if (createdAt == null) {
            throw new IllegalArgumentException("createdAt cannot be null");
        }
    }

    public static TagCorrelacao of(
            InsightTagId tagId,
            ScoreConfiabilidade score
    ) {
        return new TagCorrelacao(
                tagId,
                score,
                Instant.now()
        );
    }
}

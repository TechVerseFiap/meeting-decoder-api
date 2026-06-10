package br.com.meetingdecoder.domain.insight.model;

import java.time.Instant;

/**
 * Value Object representing a correlation between an Insight and an InsightTag (classification).
 * Replaces the INSIGHT_TAG_REL join table at the domain level.
 * Immutable and identifies the tag and the confidence of the correlation.
 */
public record TagCorrelacao(
        InsightTagId tagId,
        ScoreConfiabilidade score,
        Instant createdAt) {

    /**
     * Constructor with null validation.
     * All fields are required and cannot be null.
     */
    public TagCorrelacao {
        if (tagId == null) throw new NullPointerException("tagId cannot be null");
        if (score == null) throw new NullPointerException("score cannot be null");
        if (createdAt == null) throw new NullPointerException("createdAt cannot be null");
    }
}

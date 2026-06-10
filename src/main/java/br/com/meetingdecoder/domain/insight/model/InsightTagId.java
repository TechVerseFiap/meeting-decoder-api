package br.com.meetingdecoder.domain.insight.model;

import java.util.Objects;
import java.util.UUID;

/**
 * Typed UUID wrapper for InsightTag identifiers.
 * Ensures type safety and domain ubiquitous language.
 */
public record InsightTagId(UUID value) {
    /**
     * Constructor with null validation.
     */
    public InsightTagId {
        Objects.requireNonNull(value, "InsightTagId cannot be null");
    }

    /**
     * Factory method to create InsightTagId from a UUID.
     *
     * @param value the UUID value
     * @return a new InsightTagId
     * @throws NullPointerException if value is null
     */
    public static InsightTagId of(UUID value) {
        return new InsightTagId(value);
    }

    /**
     * Factory method to create InsightTagId from a String UUID.
     *
     * @param value the UUID as a string
     * @return a new InsightTagId
     * @throws IllegalArgumentException if value is not a valid UUID string
     * @throws NullPointerException if value is null
     */
    public static InsightTagId of(String value) {
        return new InsightTagId(UUID.fromString(value));
    }
}

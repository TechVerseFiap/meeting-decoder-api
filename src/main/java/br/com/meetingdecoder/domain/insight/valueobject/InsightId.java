package br.com.meetingdecoder.domain.insight.valueobject;

import java.util.Objects;
import java.util.UUID;

/**
 * Typed UUID wrapper for Insight identifiers.
 * Ensures type safety and domain ubiquitous language.
 */
public record InsightId(UUID value) {
    /**
     * Constructor with null validation.
     */
    public InsightId {
        Objects.requireNonNull(value, "InsightId cannot be null");
    }

    /**
     * Factory method to create InsightId from a UUID.
     *
     * @param value the UUID value
     * @return a new InsightId
     * @throws NullPointerException if value is null
     */
    public static InsightId of(UUID value) {
        return new InsightId(value);
    }

    /**
     * Factory method to create InsightId from a String UUID.
     *
     * @param value the UUID as a string
     * @return a new InsightId
     * @throws IllegalArgumentException if value is not a valid UUID string
     * @throws NullPointerException if value is null
     */
    public static InsightId of(String value) {
        return new InsightId(UUID.fromString(value));
    }
}

package br.com.meetingdecoder.domain.insight.valueobject;

import java.util.Objects;
import java.util.UUID;

/**
 * Typed UUID wrapper for Produto (service/product) identifiers.
 * Ensures type safety and domain ubiquitous language.
 */
public record ProdutoId(UUID value) {
    /**
     * Constructor with null validation.
     */
    public ProdutoId {
        Objects.requireNonNull(value, "ProdutoId cannot be null");
    }

    /**
     * Factory method to create ProdutoId from a UUID.
     *
     * @param value the UUID value
     * @return a new ProdutoId
     * @throws NullPointerException if value is null
     */
    public static ProdutoId of(UUID value) {
        return new ProdutoId(value);
    }

    /**
     * Factory method to create ProdutoId from a String UUID.
     *
     * @param value the UUID as a string
     * @return a new ProdutoId
     * @throws IllegalArgumentException if value is not a valid UUID string
     * @throws NullPointerException if value is null
     */
    public static ProdutoId of(String value) {
        return new ProdutoId(UUID.fromString(value));
    }
}

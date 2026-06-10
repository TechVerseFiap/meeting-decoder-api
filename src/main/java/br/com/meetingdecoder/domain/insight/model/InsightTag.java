package br.com.meetingdecoder.domain.insight.model;

import br.com.meetingdecoder.domain.shared.validation.DomainErrorCode;
import br.com.meetingdecoder.domain.shared.validation.ErrorCollector;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * Aggregate Root representing a classification tag/label for insights.
 * Tags allow insights to be categorized and organized for dashboard filtering and analysis.
 */
public final class InsightTag {
    private final InsightTagId id;
    private final String nome;
    private final Instant createdAt;

    /**
     * Private constructor. Use restore() or create() factory methods instead.
     */
    private InsightTag(InsightTagId id, String nome, Instant createdAt) {
        this.id = id;
        this.nome = nome;
        this.createdAt = createdAt;
    }

    /**
     * Reconstitution factory for creating InsightTag from persisted state.
     * Used by mappers when hydrating from database.
     *
     * @param id the unique identifier (required)
     * @param nome the tag name (required, non-blank, unique)
     * @param createdAt the creation timestamp (required)
     * @return a new InsightTag instance
     * @throws DomainValidationException if any required field is invalid
     */
    public static InsightTag restore(InsightTagId id, String nome, Instant createdAt) {
        return new InsightTag(id, nome, createdAt);
    }

    /**
     * Factory for creating a new InsightTag instance.
     * Domain generates a new UUID for the tag id.
     * Timestamp is set to current time.
     *
     * @param nome the tag name (required, non-blank)
     * @return a new InsightTag instance with generated id and current timestamp
     * @throws DomainValidationException if validation fails
     */
    public static InsightTag create(String nome) {
        ErrorCollector.builder()
                .requireNotNull(nome, "nome", DomainErrorCode.EMPTY_FIELD)
                .requireNotBlank(nome, "nome", DomainErrorCode.EMPTY_FIELD)
                .validate();

        InsightTagId id = InsightTagId.of(UUID.randomUUID());
        Instant now = Instant.now();
        return new InsightTag(id, nome, now);
    }

    /**
     * Returns the unique identifier of this tag.
     *
     * @return the InsightTagId
     */
    public InsightTagId getId() {
        return id;
    }

    /**
     * Returns the tag name.
     * Business rule: the name must be unique across all tags.
     *
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Returns the creation timestamp.
     *
     * @return the createdAt
     */
    public Instant getCreatedAt() {
        return createdAt;
    }
}

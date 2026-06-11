package br.com.meetingdecoder.domain.insight.valueobject;

import br.com.meetingdecoder.domain.insight.enums.Prioridade;
import br.com.meetingdecoder.domain.shared.validation.DomainErrorCode;
import br.com.meetingdecoder.domain.shared.validation.ErrorCollector;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

/**
 * Entity representing a recommended action derived from an Insight.
 * Owned by and part of the Insight aggregate; has its own identity but cannot be
 * persisted or loaded independently of its parent Insight.
 */
public final class AcaoRecomendada {
    private final UUID id;
    private final String titulo;
    private final String descricao;
    private final Prioridade prioridade;
    private final Instant prazo;
    private final ScoreConfiabilidade scoreConfiabilidade;
    private final Instant createdAt;
    private final Instant updatedAt;

    /**
     * Private constructor. Use restore() or create() factory methods instead.
     */
    private AcaoRecomendada(
            UUID id,
            String titulo,
            String descricao,
            Prioridade prioridade,
            Instant prazo,
            ScoreConfiabilidade scoreConfiabilidade,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.prioridade = prioridade;
        this.prazo = prazo;
        this.scoreConfiabilidade = scoreConfiabilidade;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Reconstitution factory for creating AcaoRecomendada from persisted state.
     * Used by mappers when hydrating from database.
     *
     * @param id the unique identifier (required)
     * @param titulo the action title (required, non-blank)
     * @param descricao the action description (required, non-blank)
     * @param prioridade the priority level (required)
     * @param prazo the deadline for the action (may be null)
     * @param scoreConfiabilidade the confidence score of the recommendation (required)
     * @param createdAt the creation timestamp (required)
     * @param updatedAt the last update timestamp (required)
     * @return a new AcaoRecomendada instance
     * @throws DomainValidationException if any required field is invalid
     */
    public static AcaoRecomendada restore(
            UUID id,
            String titulo,
            String descricao,
            Prioridade prioridade,
            Instant prazo,
            ScoreConfiabilidade scoreConfiabilidade,
            Instant createdAt,
            Instant updatedAt
    ) {
        return new AcaoRecomendada(
                id,
                titulo,
                descricao,
                prioridade,
                prazo,
                scoreConfiabilidade,
                createdAt,
                updatedAt
        );
    }

    /**
     * Factory for creating a new AcaoRecomendada instance.
     * Domain generates a new UUID for the action id.
     * Timestamps are set to current time.
     *
     * @param titulo the action title (required, non-blank)
     * @param descricao the action description (required, non-blank)
     * @param prioridade the priority level (required)
     * @param prazo the deadline for the action (may be null)
     * @param scoreConfiabilidade the confidence score of the recommendation (required)
     * @return a new AcaoRecomendada instance with generated id and current timestamps
     * @throws DomainValidationException if validation fails
     */
    public static AcaoRecomendada create(
            String titulo,
            String descricao,
            Prioridade prioridade,
            Instant prazo,
            ScoreConfiabilidade scoreConfiabilidade
    ) {
        ErrorCollector.builder()
                .requireNotNull(titulo, "titulo", DomainErrorCode.EMPTY_FIELD)
                .requireNotBlank(titulo, "titulo", DomainErrorCode.EMPTY_FIELD)
                .requireNotNull(descricao, "descricao", DomainErrorCode.EMPTY_FIELD)
                .requireNotBlank(descricao, "descricao", DomainErrorCode.EMPTY_FIELD)
                .requireNotNull(prioridade, "prioridade", DomainErrorCode.EMPTY_FIELD)
                .requireNotNull(scoreConfiabilidade, "scoreConfiabilidade", DomainErrorCode.INVALID_SCORE)
                .requireInRange(scoreConfiabilidade.getValue(), 0.0, 1.0, "scoreConfiabilidade", DomainErrorCode.INVALID_SCORE)
                .validate();

        UUID id = UUID.randomUUID();
        Instant now = Instant.now();
        return new AcaoRecomendada(
                id,
                titulo,
                descricao,
                prioridade,
                prazo,
                scoreConfiabilidade,
                now,
                now
        );
    }

    /**
     * Returns the unique identifier of this action.
     *
     * @return the UUID id
     */
    public UUID getId() {
        return id;
    }

    /**
     * Returns the action title.
     *
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Returns the action description.
     *
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Returns the action priority level.
     *
     * @return the prioridade
     */
    public Prioridade getPrioridade() {
        return prioridade;
    }

    /**
     * Returns the action deadline if set.
     *
     * @return the prazo deadline, or empty if not defined
     */
    public Optional<Instant> getPrazo() {
        return Optional.ofNullable(prazo);
    }

    /**
     * Returns the confidence score of this recommendation.
     *
     * @return the scoreConfiabilidade
     */
    public ScoreConfiabilidade getScoreConfiabilidade() {
        return scoreConfiabilidade;
    }

    /**
     * Returns the creation timestamp.
     *
     * @return the createdAt
     */
    public Instant getCreatedAt() {
        return createdAt;
    }

    /**
     * Returns the last update timestamp.
     *
     * @return the updatedAt
     */
    public Instant getUpdatedAt() {
        return updatedAt;
    }
}

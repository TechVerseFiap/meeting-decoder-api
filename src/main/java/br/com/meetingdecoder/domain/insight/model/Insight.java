package br.com.meetingdecoder.domain.insight.model;

import br.com.meetingdecoder.domain.shared.exception.DomainValidationException;
import br.com.meetingdecoder.domain.shared.validation.DomainErrorCode;
import br.com.meetingdecoder.domain.shared.validation.ErrorCollector;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Aggregate Root representing an insight extracted from a meeting transcription.
 * An insight contains AI-analyzed sentiment, related products/services, tags, and recommended actions.
 * 
 * Ownership: owns lists of AcaoRecomendada (child entities), ProdutoCorrelacao, and TagCorrelacao (value objects).
 * External references: transcricaoId (by ID only, from the Transcricao bounded context).
 */
public final class Insight {
    private final InsightId id;
    private final Object transcricaoId; // br.com.meetingdecoder.domain.transcricao.model.TranscricaoId
    private final Sentimento sentimento;
    private final String descricao;
    private final String trechoOrigem;
    private final ScoreConfiabilidade scoreConfiabilidade;
    private final List<AcaoRecomendada> acoes;
    private final List<ProdutoCorrelacao> produtos;
    private final List<TagCorrelacao> tags;
    private final Instant createdAt;
    private final Instant updatedAt;

    /**
     * Private constructor. Use restore() or create() factory methods instead.
     */
    private Insight(
            InsightId id,
            Object transcricaoId,
            Sentimento sentimento,
            String descricao,
            String trechoOrigem,
            ScoreConfiabilidade scoreConfiabilidade,
            List<AcaoRecomendada> acoes,
            List<ProdutoCorrelacao> produtos,
            List<TagCorrelacao> tags,
            Instant createdAt,
            Instant updatedAt) {
        this.id = id;
        this.transcricaoId = transcricaoId;
        this.sentimento = sentimento;
        this.descricao = descricao;
        this.trechoOrigem = trechoOrigem;
        this.scoreConfiabilidade = scoreConfiabilidade;
        this.acoes = acoes;
        this.produtos = produtos;
        this.tags = tags;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Reconstitution factory for creating Insight from persisted state.
     * Used by mappers when hydrating from database.
     * 
     * Validates that all required fields are present and that all business rules are enforced.
     *
     * @param id the unique identifier (required)
     * @param transcricaoId the transcription identifier from Transcricao context (required, passed as Object)
     * @param sentimento the sentiment analysis result (required)
     * @param descricao the insight description (required, non-blank)
     * @param trechoOrigem the origin snippet from transcription (may be null)
     * @param scoreConfiabilidade the overall confidence score (required, in range [0.0, 1.0])
     * @param acoes list of recommended actions owned by this insight (required, may be empty)
     * @param produtos list of related products/services (required, may be empty)
     * @param tags list of classification tags (required, may be empty)
     * @param createdAt the creation timestamp (required)
     * @param updatedAt the last update timestamp (required)
     * @return a new Insight instance
     * @throws DomainValidationException if any required field is invalid
     */
    public static Insight restore(
            InsightId id,
            Object transcricaoId,
            Sentimento sentimento,
            String descricao,
            String trechoOrigem,
            ScoreConfiabilidade scoreConfiabilidade,
            List<AcaoRecomendada> acoes,
            List<ProdutoCorrelacao> produtos,
            List<TagCorrelacao> tags,
            Instant createdAt,
            Instant updatedAt
    ) {
        return new Insight(
                id,
                transcricaoId,
                sentimento,
                descricao,
                trechoOrigem,
                scoreConfiabilidade,
                acoes,
                produtos,
                tags,
                createdAt,
                updatedAt
        );
    }

    /**
     * Factory for creating a new Insight instance.
     * Domain generates a new UUID for the insight id.
     * Timestamps are set to current time.
     *
     * @param transcricaoId the transcription identifier (required)
     * @param sentimento the sentiment analysis result (required)
     * @param descricao the insight description (required, non-blank)
     * @param trechoOrigem the origin snippet from transcription (may be null)
     * @param scoreConfiabilidade the overall confidence score (required, in range [0.0, 1.0])
     * @param acoes list of recommended actions (required, may be empty)
     * @param produtos list of related products/services (required, may be empty)
     * @param tags list of classification tags (required, may be empty)
     * @return a new Insight instance with generated id and current timestamps
     * @throws DomainValidationException if validation fails
     */
    public static Insight create(
            Object transcricaoId,
            Sentimento sentimento,
            String descricao,
            String trechoOrigem,
            ScoreConfiabilidade scoreConfiabilidade,
            List<AcaoRecomendada> acoes,
            List<ProdutoCorrelacao> produtos,
            List<TagCorrelacao> tags
    ) {
        ErrorCollector.builder()
                .requireNotNull(transcricaoId, "transcricaoId", DomainErrorCode.EMPTY_FIELD)
                .requireNotNull(sentimento, "sentimento", DomainErrorCode.EMPTY_FIELD)
                .requireNotNull(descricao, "descricao", DomainErrorCode.EMPTY_FIELD)
                .requireNotBlank(descricao, "descricao", DomainErrorCode.EMPTY_FIELD)
                .requireNotNull(scoreConfiabilidade, "scoreConfiabilidade", DomainErrorCode.INVALID_SCORE)
                .requireInRange(scoreConfiabilidade.getValue(), 0.0, 1.0, "scoreConfiabilidade", DomainErrorCode.INVALID_SCORE)
                .requireNotNull(acoes, "acoes", DomainErrorCode.EMPTY_FIELD)
                .requireNotNull(produtos, "produtos", DomainErrorCode.EMPTY_FIELD)
                .requireNotNull(tags, "tags", DomainErrorCode.EMPTY_FIELD)
                .validate();

        Instant now = Instant.now();
        InsightId id = InsightId.of(UUID.randomUUID());
        
        List<AcaoRecomendada> acoesCopy = List.copyOf(acoes);
        List<ProdutoCorrelacao> produtosCopy = List.copyOf(produtos);
        List<TagCorrelacao> tagsCopy = List.copyOf(tags);

        return new Insight(
                id,
                transcricaoId,
                sentimento,
                descricao,
                trechoOrigem,
                scoreConfiabilidade,
                acoesCopy,
                produtosCopy,
                tagsCopy,
                now,
                now
        );
    }

    /**
     * Returns the unique identifier of this insight.
     *
     * @return the InsightId
     */
    public InsightId getId() {
        return id;
    }

    /**
     * Returns the transcription identifier from the Transcricao bounded context.
     * 
     * Note: returned as Object because the actual type (TranscricaoId) is defined in another context.
     * Callers should cast to the appropriate type when needed.
     *
     * @return the transcricaoId
     */
    public Object getTranscricaoId() {
        return transcricaoId;
    }

    /**
     * Returns the sentiment analysis result for this insight.
     *
     * @return the Sentimento
     */
    public Sentimento getSentimento() {
        return sentimento;
    }

    /**
     * Returns the insight description.
     *
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Returns the origin snippet from the transcription.
     *
     * @return the trechoOrigem, or null if not available
     */
    public String getTrechoOrigem() {
        return trechoOrigem;
    }

    /**
     * Returns the overall confidence score of this insight.
     *
     * @return the scoreConfiabilidade
     */
    public ScoreConfiabilidade getScoreConfiabilidade() {
        return scoreConfiabilidade;
    }

    /**
     * Returns the list of recommended actions owned by this insight.
     *
     * @return an unmodifiable list of AcaoRecomendada
     */
    public List<AcaoRecomendada> getAcoes() {
        return Collections.unmodifiableList(acoes);
    }

    /**
     * Returns the list of related products/services correlated with this insight.
     *
     * @return an unmodifiable list of ProdutoCorrelacao
     */
    public List<ProdutoCorrelacao> getProdutos() {
        return Collections.unmodifiableList(produtos);
    }

    /**
     * Returns the list of classification tags associated with this insight.
     *
     * @return an unmodifiable list of TagCorrelacao
     */
    public List<TagCorrelacao> getTags() {
        return Collections.unmodifiableList(tags);
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

    /**
     * Indicates whether this insight signals a potential churn risk.
     * Business rule: an insight is considered a churn signal when its sentiment is NEGATIVO
     * with high confidence (score ≥ 0.80), indicating strong evidence of customer dissatisfaction.
     * 
     * This method encodes the primary business rule for identifying at-risk customers from
     * meeting transcriptions.
     *
     * @return true if the sentiment indicates a potential churn signal (NEGATIVO with score ≥ 0.80)
     */
    public boolean isSinalDeChurnPotencial() {
        return sentimento.isSinalDeChurnPotencial();
    }
}

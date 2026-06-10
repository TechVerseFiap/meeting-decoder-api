package br.com.meetingdecoder.domain.insight.model;

import br.com.meetingdecoder.domain.insight.valueobject.*;
<<<<<<< Updated upstream
import br.com.meetingdecoder.domain.shared.exception.DomainValidationException;
=======
>>>>>>> Stashed changes
import br.com.meetingdecoder.domain.shared.validation.DomainErrorCode;
import br.com.meetingdecoder.domain.shared.validation.ErrorCollector;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public final class Insight {
    private final InsightId id;
    private final Object transcricaoId;
    private final Sentimento sentimento;
    private final String descricao;
    private final String trechoOrigem;
    private final ScoreConfiabilidade scoreConfiabilidade;
    private final List<AcaoRecomendada> acoes;
    private final List<ProdutoCorrelacao> produtos;
    private final List<TagCorrelacao> tags;
    private final Instant createdAt;
    private final Instant updatedAt;

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
                null
        );
    }

    public InsightId getId() {
        return id;
    }

    public Object getTranscricaoId() {
        return transcricaoId;
    }

    public Sentimento getSentimento() {
        return sentimento;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getTrechoOrigem() {
        return trechoOrigem;
    }

    public ScoreConfiabilidade getScoreConfiabilidade() {
        return scoreConfiabilidade;
    }

    public List<AcaoRecomendada> getAcoes() {
        return Collections.unmodifiableList(acoes);
    }

    public List<ProdutoCorrelacao> getProdutos() {
        return Collections.unmodifiableList(produtos);
    }

    public List<TagCorrelacao> getTags() {
        return Collections.unmodifiableList(tags);
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public boolean isSinalDeChurnPotencial() {
        return sentimento.isSinalDeChurnPotencial();
    }
}

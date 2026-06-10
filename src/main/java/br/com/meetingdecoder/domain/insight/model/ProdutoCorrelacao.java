package br.com.meetingdecoder.domain.insight.model;

import java.time.Instant;

/**
 * Value Object representing a correlation between an Insight and a Produto (service).
 * Replaces the INSIGHT_SERVICO join table at the domain level.
 * Immutable and identifies the product and the confidence of the correlation.
 */
public record ProdutoCorrelacao(
        ProdutoId produtoId,
        ScoreConfiabilidade score,
        Instant createdAt) {

    /**
     * Constructor with null validation.
     * All fields are required and cannot be null.
     */
    public ProdutoCorrelacao {
        if (produtoId == null) throw new NullPointerException("produtoId cannot be null");
        if (score == null) throw new NullPointerException("score cannot be null");
        if (createdAt == null) throw new NullPointerException("createdAt cannot be null");
    }
}

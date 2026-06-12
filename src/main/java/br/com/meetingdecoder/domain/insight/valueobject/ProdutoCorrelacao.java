package br.com.meetingdecoder.domain.insight.valueobject;

import java.time.Instant;

public record ProdutoCorrelacao(
        ProdutoId produtoId,
        ScoreConfiabilidade score,
        Instant createdAt
) {

    public ProdutoCorrelacao {
        if (produtoId == null) {
            throw new IllegalArgumentException("produtoId cannot be null");
        }

        if (score == null) {
            throw new IllegalArgumentException("score cannot be null");
        }

        if (createdAt == null) {
            throw new IllegalArgumentException("createdAt cannot be null");
        }
    }

    public static ProdutoCorrelacao of(
            ProdutoId produtoId,
            ScoreConfiabilidade score
    ) {
        return new ProdutoCorrelacao(
                produtoId,
                score,
                Instant.now()
        );
    }
}

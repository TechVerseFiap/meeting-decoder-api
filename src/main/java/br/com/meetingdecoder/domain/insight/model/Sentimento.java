package br.com.meetingdecoder.domain.insight.model;

import java.util.Objects;

/**
 * Value Object representing a sentiment analysis result.
 * Combines the sentiment polaridade (positive/neutral/negative) with its confidence score.
 */
public final class Sentimento {
    private final Polaridade polaridade;
    private final ScoreConfiabilidade scoreConfiabilidade;

    /**
     * Enum representing sentiment polarity.
     */
    public enum Polaridade {
        POSITIVO,
        NEUTRO,
        NEGATIVO
    }

    /**
     * Constructor for Sentimento with null validation.
     *
     * @param polaridade the sentiment polarity
     * @param scoreConfiabilidade the confidence score of the sentiment
     * @throws NullPointerException if any argument is null
     */
    public Sentimento(Polaridade polaridade, ScoreConfiabilidade scoreConfiabilidade) {
        this.polaridade = Objects.requireNonNull(polaridade, "Polaridade cannot be null");
        this.scoreConfiabilidade = Objects.requireNonNull(scoreConfiabilidade,
                "ScoreConfiabilidade cannot be null");
    }

    /**
     * Returns the sentiment polarity.
     *
     * @return the sentiment direction (POSITIVO, NEUTRO, or NEGATIVO)
     */
    public Polaridade getPolaridade() {
        return polaridade;
    }

    /**
     * Returns the confidence score of this sentiment analysis.
     *
     * @return the confidence score in range [0.0, 1.0]
     */
    public ScoreConfiabilidade getScoreConfiabilidade() {
        return scoreConfiabilidade;
    }

    /**
     * Indicates whether this sentiment signals a potential churn risk.
     * Business rule: a sentiment is considered a churn signal when it is NEGATIVO
     * with high confidence (score ≥ 0.80), indicating strong evidence of customer dissatisfaction.
     *
     * @return true if polaridade is NEGATIVO and score is high (≥ 0.80)
     */
    public boolean isSinalDeChurnPotencial() {
        return polaridade == Polaridade.NEGATIVO && scoreConfiabilidade.isAlta();
    }
}

package br.com.meetingdecoder.domain.insight.valueobject;

/**
 * Value Object representing a confidence score in the range [0.0, 1.0].
 * Provides threshold helpers for categorizing confidence levels.
 */
public final class ScoreConfiabilidade {
    private static final double MIN_VALUE = 0.0;
    private static final double MAX_VALUE = 1.0;
    private static final double LIMIAR_ALTA = 0.80;
    private static final double LIMIAR_MEDIA = 0.50;

    private final double value;

    /**
     * Constructor with validation that score is within [0.0, 1.0].
     *
     * @param value the confidence score
     * @throws IllegalArgumentException if value is not in range [0.0, 1.0]
     */
    public ScoreConfiabilidade(double value) {
        if (value < MIN_VALUE || value > MAX_VALUE) {
            throw new IllegalArgumentException(
                    "ScoreConfiabilidade must be between 0.0 and 1.0, but was " + value);
        }
        this.value = value;
    }

    /**
     * Returns the raw confidence score value.
     *
     * @return the score in range [0.0, 1.0]
     */
    public double getValue() {
        return value;
    }

    /**
     * Indicates whether this score is considered high confidence (≥ 0.80).
     * Business rule: high confidence indicates AI model certainty.
     *
     * @return true if score >= 0.80
     */
    public boolean isAlta() {
        return value >= LIMIAR_ALTA;
    }

    /**
     * Indicates whether this score is considered medium confidence (≥ 0.50 and < 0.80).
     * Business rule: medium confidence indicates moderate model certainty.
     *
     * @return true if score >= 0.50
     */
    public boolean isMedia() {
        return value >= LIMIAR_MEDIA && value < LIMIAR_ALTA;
    }

    /**
     * Indicates whether this score is considered low confidence (< 0.50).
     * Business rule: low confidence indicates low model certainty.
     *
     * @return true if score < 0.50
     */
    public boolean isBaixa() {
        return value < LIMIAR_MEDIA;
    }
}

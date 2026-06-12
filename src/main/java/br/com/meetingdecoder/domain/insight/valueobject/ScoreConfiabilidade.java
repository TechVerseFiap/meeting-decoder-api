package br.com.meetingdecoder.domain.insight.valueobject;

public final class ScoreConfiabilidade {
    private static final double MIN_VALUE = 0.0;
    private static final double MAX_VALUE = 1.0;

    private static final double LIMIAR_ALTA = 0.80;
    private static final double LIMIAR_MEDIA = 0.50;

    private final double value;

    private ScoreConfiabilidade(double value) {
        if (value < MIN_VALUE || value > MAX_VALUE) {
            throw new IllegalArgumentException(
                    "ScoreConfiabilidade must be between 0.0 and 1.0, but was " + value
            );
        }
        this.value = value;
    }

    public static ScoreConfiabilidade of(double value) {
        return new ScoreConfiabilidade(value);
    }

    public static ScoreConfiabilidade alta() {
        return new ScoreConfiabilidade(0.90);
    }

    public static ScoreConfiabilidade media() {
        return new ScoreConfiabilidade(0.65);
    }

    public static ScoreConfiabilidade baixa() {
        return new ScoreConfiabilidade(0.25);
    }

    public double value() {
        return value;
    }

    public boolean isAlta() {
        return value >= LIMIAR_ALTA;
    }

    public boolean isMedia() {
        return value >= LIMIAR_MEDIA && value < LIMIAR_ALTA;
    }

    public boolean isBaixa() {
        return value < LIMIAR_MEDIA;
    }
}

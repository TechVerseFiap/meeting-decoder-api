package br.com.meetingdecoder.domain.shared.valueobject;

public final class ScoreConfiabilidade {
    private static final double THRESHOLD_ALTA = 0.7;
    private static final double THRESHOLD_BAIXA = 0.4;

    private final double value;

    private ScoreConfiabilidade(double value) {
        if (value < 0.0 || value > 1.0)
            throw new IllegalArgumentException("O score de confiabilidade precisa estar entre 0.0 e 1.0: " + value);

        this.value = value;
    }

    public static ScoreConfiabilidade of(double value) {
        return new ScoreConfiabilidade(value);
    }

    public static ScoreConfiabilidade medio() {
        return new ScoreConfiabilidade(0.5);
    }

    public NivelRelevancia getNivel() {
        if (isAlta()) return NivelRelevancia.ALTA;
        if (isMedia()) return NivelRelevancia.MEDIA;
        return NivelRelevancia.BAIXA;
    }

    public boolean isAlta() {
        return value >= THRESHOLD_ALTA;
    }

    public boolean isMedia() {
        return value > THRESHOLD_BAIXA && value < THRESHOLD_ALTA;
    }

    public boolean isBaixa() {
        return value <= THRESHOLD_BAIXA;
    }
}

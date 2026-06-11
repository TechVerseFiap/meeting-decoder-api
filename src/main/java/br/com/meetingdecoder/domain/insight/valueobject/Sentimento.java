package br.com.meetingdecoder.domain.insight.valueobject;

import br.com.meetingdecoder.domain.insight.enums.Polaridade;
import br.com.meetingdecoder.domain.shared.valueobject.ScoreConfiabilidade;

public final class Sentimento {
    private final Polaridade polaridade;
    private final ScoreConfiabilidade score;

    private Sentimento(Polaridade polaridade, ScoreConfiabilidade score) {
        if (polaridade == null)
            throw new IllegalArgumentException("Polaridade do sentimento é obrigatória");
        this.polaridade = polaridade;
        this.score = score;
    }

    public static Sentimento of(Polaridade polaridade, ScoreConfiabilidade score) {
        return new Sentimento(polaridade, score);
    }
    public static Sentimento of(Polaridade polaridade, double score) {
        return new Sentimento(polaridade, ScoreConfiabilidade.of(score));
    }

    public static Sentimento neutro() {
        return new Sentimento(Polaridade.NEUTRO, ScoreConfiabilidade.medio());
    }

    public Polaridade getPolaridade() { return polaridade; }

    public boolean isNegativo() { return polaridade == Polaridade.NEGATIVO; }
    public boolean isNeutro() { return polaridade == Polaridade.NEUTRO; }
    public boolean isPositivo() { return polaridade == Polaridade.POSITIVO; }

    /**
     * A negative sentiment with high confidence is a strong churn signal.
     */
    public boolean isSinalDeChurnPotencial() {
        return polaridade == Polaridade.NEGATIVO && score.isAlta();
    }
}

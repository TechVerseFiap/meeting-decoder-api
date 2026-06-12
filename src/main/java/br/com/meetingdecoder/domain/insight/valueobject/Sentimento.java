package br.com.meetingdecoder.domain.insight.valueobject;

import br.com.meetingdecoder.domain.insight.enums.Polaridade;

public final class Sentimento {

    private final Polaridade polaridade;
    private final ScoreConfiabilidade score;

    private Sentimento(
            Polaridade polaridade,
            ScoreConfiabilidade score
    ) {
        if (polaridade == null) {
            throw new IllegalArgumentException(
                    "Polaridade do sentimento é obrigatória"
            );
        }

        if (score == null) {
            throw new IllegalArgumentException(
                    "Score do sentimento é obrigatório"
            );
        }

        this.polaridade = polaridade;
        this.score = score;
    }

    public static Sentimento of(
            Polaridade polaridade,
            ScoreConfiabilidade score
    ) {
        return new Sentimento(polaridade, score);
    }

    public static Sentimento of(
            Polaridade polaridade,
            double score
    ) {
        return new Sentimento(
                polaridade,
                ScoreConfiabilidade.of(score)
        );
    }

    public static Sentimento neutro() {
        return new Sentimento(
                Polaridade.NEUTRO,
                ScoreConfiabilidade.media()
        );
    }

    public Polaridade getPolaridade() {
        return polaridade;
    }

    public ScoreConfiabilidade getScore() {
        return score;
    }

    public boolean isNegativo() {
        return polaridade == Polaridade.NEGATIVO;
    }

    public boolean isNeutro() {
        return polaridade == Polaridade.NEUTRO;
    }

    public boolean isPositivo() {
        return polaridade == Polaridade.POSITIVO;
    }

    public boolean isSinalDeChurnPotencial() {
        return isNegativo() && score.isAlta();
    }
}

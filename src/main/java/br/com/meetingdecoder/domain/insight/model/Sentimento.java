package br.com.meetingdecoder.domain.insight.model;

import br.com.meetingdecoder.domain.insight.valueobject.ScoreConfiabilidade;

import java.util.Objects;

public final class Sentimento {
    private final Polaridade polaridade;
    private final ScoreConfiabilidade scoreConfiabilidade;

    public enum Polaridade {
        POSITIVO,
        NEUTRO,
        NEGATIVO
    }

    public Sentimento(Polaridade polaridade, ScoreConfiabilidade scoreConfiabilidade) {
        this.polaridade = Objects.requireNonNull(polaridade, "Polaridade cannot be null");
        this.scoreConfiabilidade = Objects.requireNonNull(scoreConfiabilidade,
                "ScoreConfiabilidade cannot be null");
    }

    public Polaridade getPolaridade() {
        return polaridade;
    }

    public ScoreConfiabilidade getScoreConfiabilidade() {
        return scoreConfiabilidade;
    }

    public boolean isSinalDeChurnPotencial() {
        return polaridade == Polaridade.NEGATIVO && scoreConfiabilidade.isAlta();
    }
}

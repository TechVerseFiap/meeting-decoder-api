package br.com.meetingdecoder.domain.insight.valueobject;

import java.math.BigDecimal;
import java.util.Optional;

public final class FaixaPreco {
    private final BigDecimal minimo;
    private final BigDecimal maximo;

    public FaixaPreco(BigDecimal minimo, BigDecimal maximo) {
        if (minimo != null && maximo != null && minimo.compareTo(maximo) > 0) {
            throw new IllegalArgumentException(
                    "Faixa preço mínimo must be <= máximo, but minimo=" + minimo + " maximo=" + maximo);
        }
        this.minimo = minimo;
        this.maximo = maximo;
    }

    public Optional<BigDecimal> minimo() {
        return Optional.ofNullable(minimo);
    }

    public Optional<BigDecimal> maximo() {
        return Optional.ofNullable(maximo);
    }
}

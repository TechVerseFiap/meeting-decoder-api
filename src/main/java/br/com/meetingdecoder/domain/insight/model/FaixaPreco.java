package br.com.meetingdecoder.domain.insight.model;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

/**
 * Value Object representing a price range for products/services.
 * Both minimum and maximum are optional independently; validates that
 * minimum ≤ maximum when both are present.
 */
public final class FaixaPreco {
    private final BigDecimal minimo;
    private final BigDecimal maximo;

    /**
     * Constructor with price range validation.
     * Either or both values may be null, but if both are present, minimo must be ≤ maximo.
     *
     * @param minimo the minimum price (may be null)
     * @param maximo the maximum price (may be null)
     * @throws IllegalArgumentException if minimo > maximo
     */
    public FaixaPreco(BigDecimal minimo, BigDecimal maximo) {
        if (minimo != null && maximo != null && minimo.compareTo(maximo) > 0) {
            throw new IllegalArgumentException(
                    "Faixa preço mínimo must be <= máximo, but minimo=" + minimo + " maximo=" + maximo);
        }
        this.minimo = minimo;
        this.maximo = maximo;
    }

    /**
     * Returns the minimum price of the range.
     *
     * @return the minimum price, or empty if not defined
     */
    public Optional<BigDecimal> getMinimo() {
        return Optional.ofNullable(minimo);
    }

    /**
     * Returns the maximum price of the range.
     *
     * @return the maximum price, or empty if not defined
     */
    public Optional<BigDecimal> getMaximo() {
        return Optional.ofNullable(maximo);
    }
}

package br.com.meetingdecoder.domain.insight.model;

import br.com.meetingdecoder.domain.shared.validation.DomainErrorCode;
import br.com.meetingdecoder.domain.shared.validation.ErrorCollector;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Aggregate Root representing a product or service in the company's catalog.
 * Products can be associated with insights to suggest relevant services.
 */
public final class Produto {
    private final ProdutoId id;
    private final String nome;
    private final String categoria;
    private final String descricao;
    private final String linha;
    private final FaixaPreco faixaPreco;
    private final Instant createdAt;
    private final Instant updatedAt;

    /**
     * Private constructor. Use restore() factory method instead.
     */
    private Produto(
            ProdutoId id,
            String nome,
            String categoria,
            String descricao,
            String linha,
            FaixaPreco faixaPreco,
            Instant createdAt,
            Instant updatedAt) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.descricao = descricao;
        this.linha = linha;
        this.faixaPreco = faixaPreco;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Reconstitution factory for creating Produto from persisted state.
     * Used by mappers when hydrating from database.
     *
     * @param id the unique identifier (required)
     * @param nome the product name (required, non-blank, unique)
     * @param categoria the product category (may be null)
     * @param descricao the product description (may be null)
     * @param linha the product line (may be null)
     * @param faixaPreco the price range (may be null)
     * @param createdAt the creation timestamp (required)
     * @param updatedAt the last update timestamp (required)
     * @return a new Produto instance
     * @throws DomainValidationException if any required field is invalid
     */
    public static Produto restore(
            ProdutoId id,
            String nome,
            String categoria,
            String descricao,
            String linha,
            FaixaPreco faixaPreco,
            Instant createdAt,
            Instant updatedAt
    ) {
        return new Produto(
                id,
                nome,
                categoria,
                descricao,
                linha,
                faixaPreco,
                createdAt,
                updatedAt
        );
    }

    /**
     * Factory for creating a new Produto instance.
     * Domain generates a new UUID for the product id.
     * Timestamps are set to current time.
     *
     * @param nome the product name (required, non-blank)
     * @param categoria the product category (may be null)
     * @param descricao the product description (may be null)
     * @param linha the product line (may be null)
     * @param faixaPrecoMinimo the minimum price (may be null)
     * @param faixaPrecoMaximo the maximum price (may be null)
     * @return a new Produto instance with generated id and timestamps
     * @throws DomainValidationException if validation fails
     */
    public static Produto create(
            String nome,
            String categoria,
            String descricao,
            String linha,
            BigDecimal faixaPrecoMinimo,
            BigDecimal faixaPrecoMaximo
    ) {
        FaixaPreco faixaPreco = null;
        if (faixaPrecoMinimo != null || faixaPrecoMaximo != null) {
            faixaPreco = new FaixaPreco(faixaPrecoMinimo, faixaPrecoMaximo);
        }

        new ErrorCollector()
                .requireNotNull(nome, "nome", DomainErrorCode.EMPTY_FIELD)
                .requireNotBlank(nome, "nome", DomainErrorCode.EMPTY_FIELD)
                .requireMinLessOrEqualMax(faixaPrecoMinimo, faixaPrecoMaximo,
                        "faixaPrecoMinimo", "faixaPrecoMaximo", DomainErrorCode.INVALID_RANGE)
                .validate();

        Instant now = Instant.now();
        ProdutoId id = ProdutoId.of(UUID.randomUUID());
        return new Produto(id, nome, categoria, descricao, linha, faixaPreco, now, now);
    }

    /**
     * Returns the unique identifier of this product.
     *
     * @return the ProdutoId
     */
    public ProdutoId getId() {
        return id;
    }

    /**
     * Returns the product name.
     * Business rule: the name must be unique across all products.
     *
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Returns the product category.
     *
     * @return the categoria, or null if not defined
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Returns the product description.
     *
     * @return the descricao, or null if not defined
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Returns the product line.
     *
     * @return the linha, or null if not defined
     */
    public String getLinha() {
        return linha;
    }

    /**
     * Returns the price range of this product.
     *
     * @return the FaixaPreco, or empty if not defined
     */
    public Optional<FaixaPreco> getFaixaPreco() {
        return Optional.ofNullable(faixaPreco);
    }

    /**
     * Returns the creation timestamp.
     *
     * @return the createdAt
     */
    public Instant getCreatedAt() {
        return createdAt;
    }

    /**
     * Returns the last update timestamp.
     *
     * @return the updatedAt
     */
    public Instant getUpdatedAt() {
        return updatedAt;
    }
}

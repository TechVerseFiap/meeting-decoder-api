package br.com.meetingdecoder.domain.insight.model;

import br.com.meetingdecoder.domain.insight.valueobject.FaixaPreco;
import br.com.meetingdecoder.domain.insight.valueobject.ProdutoId;
import br.com.meetingdecoder.domain.shared.validation.DomainErrorCode;
import br.com.meetingdecoder.domain.shared.validation.ErrorCollector;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public final class Produto {
    private final ProdutoId id;
    private final String nome;
    private final String categoria;
    private final String descricao;
    private final String linha;
    private final FaixaPreco faixaPreco;
    private final Instant createdAt;
    private final Instant updatedAt;

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

        ErrorCollector.builder()
                .requireNotNull(nome, "nome", DomainErrorCode.EMPTY_FIELD)
                .requireNotBlank(nome, "nome", DomainErrorCode.EMPTY_FIELD)
                .requireMinLessOrEqualMax(faixaPrecoMinimo, faixaPrecoMaximo,
                        "faixaPrecoMinimo", "faixaPrecoMaximo", DomainErrorCode.INVALID_RANGE)
                .validate();

        Instant now = Instant.now();
        ProdutoId id = ProdutoId.of(UUID.randomUUID());
        return new Produto(id, nome, categoria, descricao, linha, faixaPreco, now, null);
    }

    public Produto update(
            String nome,
            String categoria,
            String descricao,
            String linha,
            BigDecimal faixaPrecoMinimo,
            BigDecimal faixaPrecoMaximo
    ) {
        FaixaPreco novaFaixa = null;
        if (faixaPrecoMinimo != null || faixaPrecoMaximo != null) {
            novaFaixa = new FaixaPreco(
                    faixaPrecoMinimo,
                    faixaPrecoMaximo
            );
        }
        return new Produto(
                this.id,
                nome != null ? nome : this.nome,
                categoria != null ? categoria : this.categoria,
                descricao != null ? descricao : this.descricao,
                linha != null ? linha : this.linha,
                novaFaixa != null ? novaFaixa : this.faixaPreco,
                this.createdAt,
                Instant.now()
        );
    }

    public ProdutoId id() {
        return id;
    }

    public String nome() {
        return nome;
    }

    public String categoria() {
        return categoria;
    }

    public String descricao() {
        return descricao;
    }

    public String linha() {
        return linha;
    }

    public Optional<FaixaPreco> faixaPreco() {
        return Optional.ofNullable(faixaPreco);
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }
}

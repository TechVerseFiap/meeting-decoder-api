package br.com.meetingdecoder.application.dto.produto;

import br.com.meetingdecoder.domain.insight.model.Produto;
import br.com.meetingdecoder.domain.insight.valueobject.FaixaPreco;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record ProdutoOutput(
        UUID id,
        String nome,
        String categoria,
        String descricao,
        String linha,
        BigDecimal faixaPrecoMinimo,
        BigDecimal faixaPrecoMaximo,
        Instant createdAt,
        Instant updatedAt
) {

    public static ProdutoOutput from(
            Produto produto
    ) {

        BigDecimal minimo = produto.faixaPreco()
                .flatMap(FaixaPreco::minimo)
                .orElse(null);

        BigDecimal maximo = produto.faixaPreco()
                .flatMap(FaixaPreco::maximo)
                .orElse(null);

        return new ProdutoOutput(
                produto.id().value(),
                produto.nome(),
                produto.categoria(),
                produto.descricao(),
                produto.linha(),
                minimo,
                maximo,
                produto.createdAt(),
                produto.updatedAt()
        );
    }
}

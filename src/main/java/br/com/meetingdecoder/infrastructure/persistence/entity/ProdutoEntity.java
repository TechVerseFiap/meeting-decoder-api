package br.com.meetingdecoder.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "produtos")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "descricao", columnDefinition = "CLOB")
    private String descricao;

    @Column(name = "linha")
    private String linha;

    @Column(name = "faixa_preco_minimo", precision = 19, scale = 2)
    private BigDecimal faixaPrecoMinimo;

    @Column(name = "faixa_preco_maximo", precision = 19, scale = 2)
    private BigDecimal faixaPrecoMaximo;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;
}

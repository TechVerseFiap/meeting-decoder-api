package br.com.meetingdecoder.infrastructure.persistence.entity;

import br.com.meetingdecoder.domain.insight.enums.Polaridade;
import br.com.meetingdecoder.domain.insight.enums.Prioridade;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "insights")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InsightEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "transcricao_id", nullable = false)
    private UUID transcricaoId;

    @Enumerated(EnumType.STRING)
    @Column(name = "polaridade", nullable = false)
    private Polaridade polaridade;

    @Column(name = "sentimento_score", nullable = false)
    private Double sentimentoScore;

    @Column(name = "descricao", nullable = false, columnDefinition = "CLOB")
    private String descricao;

    @Column(name = "trecho_origem", columnDefinition = "CLOB")
    private String trechoOrigem;

    @Column(name = "score_confiabilidade", nullable = false)
    private Double scoreConfiabilidade;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "insight_acoes", joinColumns = @JoinColumn(name = "insight_id"))
    @Builder.Default
    private List<AcaoRecomendadaEmbeddable> acoes = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "insight_produto_correlacoes", joinColumns = @JoinColumn(name = "insight_id"))
    @Builder.Default
    private List<ProdutoCorrelacaoEmbeddable> produtos = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "insight_tag_correlacoes", joinColumns = @JoinColumn(name = "insight_id"))
    @Builder.Default
    private List<TagCorrelacaoEmbeddable> tags = new ArrayList<>();

    // ── nested embeddables ───────────────────────────────────────────────

    @Embeddable
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AcaoRecomendadaEmbeddable {

        @Column(name = "acao_id", nullable = false)
        private UUID acaoId;

        @Column(name = "titulo", nullable = false)
        private String titulo;

        @Column(name = "descricao", columnDefinition = "CLOB")
        private String descricao;

        @Enumerated(EnumType.STRING)
        @Column(name = "prioridade", nullable = false)
        private Prioridade prioridade;

        @Column(name = "prazo")
        private Instant prazo;

        @Column(name = "score_confiabilidade", nullable = false)
        private Double scoreConfiabilidade;

        @Column(name = "created_at", nullable = false)
        private Instant createdAt;

        @Column(name = "updated_at")
        private Instant updatedAt;
    }

    @Embeddable
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProdutoCorrelacaoEmbeddable {

        @Column(name = "produto_id", nullable = false)
        private UUID produtoId;

        @Column(name = "score", nullable = false)
        private Double score;

        @Column(name = "created_at", nullable = false)
        private Instant createdAt;
    }

    @Embeddable
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TagCorrelacaoEmbeddable {

        @Column(name = "tag_id", nullable = false)
        private UUID tagId;

        @Column(name = "score", nullable = false)
        private Double score;

        @Column(name = "created_at", nullable = false)
        private Instant createdAt;
    }
}

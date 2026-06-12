package br.com.meetingdecoder.infrastructure.persistence.repository;

import br.com.meetingdecoder.domain.insight.enums.Polaridade;
import br.com.meetingdecoder.domain.insight.model.Insight;
import br.com.meetingdecoder.domain.insight.repository.IInsightRepository;
import br.com.meetingdecoder.domain.insight.valueobject.*;
import br.com.meetingdecoder.domain.sale.valueobject.ClientId;
import br.com.meetingdecoder.domain.sale.valueobject.SellerId;
import br.com.meetingdecoder.domain.transcription.valueobject.TranscriptionId;
import br.com.meetingdecoder.infrastructure.persistence.entity.InsightEntity;
import br.com.meetingdecoder.infrastructure.persistence.entity.InsightEntity.AcaoRecomendadaEmbeddable;
import br.com.meetingdecoder.infrastructure.persistence.entity.InsightEntity.ProdutoCorrelacaoEmbeddable;
import br.com.meetingdecoder.infrastructure.persistence.entity.InsightEntity.TagCorrelacaoEmbeddable;
import br.com.meetingdecoder.infrastructure.persistence.jpa.JpaInsightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class InsightRepositoryAdapter implements IInsightRepository {

    private static final double CHURN_SCORE_THRESHOLD = 0.80;
    private static final double PRIORITY_SCORE_THRESHOLD = 0.50;

    private final JpaInsightRepository jpa;

    @Override
    public Optional<Insight> findById(InsightId id) {
        return jpa.findById(id.value())
                .map(this::toDomain);
    }

    @Override
    public List<Insight> findByTranscricaoId(TranscriptionId transcricaoId) {
        return jpa.findAllByTranscricaoId(transcricaoId.value()).stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public List<Insight> findRiscosChurnAtivos(ClientId clienteId) {
        return jpa.findRiscosChurnAtivos(
                        clienteId.value(),
                        Polaridade.NEGATIVO,
                        CHURN_SCORE_THRESHOLD
                ).stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public List<Insight> findInsightsPrioritariosParaVendedor(SellerId vendedorId) {
        return jpa.findInsightsPrioritariosParaVendedor(
                        vendedorId.value(),
                        PRIORITY_SCORE_THRESHOLD
                ).stream()
                .map(this::toDomain)
                .toList();
    }

    private Insight toDomain(InsightEntity entity) {
        List<AcaoRecomendada> acoes = entity.getAcoes().stream()
                .map(this::toAcaoRecomendada)
                .toList();

        List<ProdutoCorrelacao> produtos = entity.getProdutos().stream()
                .map(this::toProdutoCorrelacao)
                .toList();

        List<TagCorrelacao> tags = entity.getTags().stream()
                .map(this::toTagCorrelacao)
                .toList();

        return Insight.restore(
                InsightId.of(entity.getId()),
                TranscriptionId.of(entity.getTranscricaoId()),
                Sentimento.of(
                        entity.getPolaridade(),
                        ScoreConfiabilidade.of(entity.getSentimentoScore())
                ),
                entity.getDescricao(),
                entity.getTrechoOrigem(),
                ScoreConfiabilidade.of(entity.getScoreConfiabilidade()),
                acoes,
                produtos,
                tags,
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    private AcaoRecomendada toAcaoRecomendada(AcaoRecomendadaEmbeddable e) {
        return AcaoRecomendada.restore(
                e.getAcaoId(),
                e.getTitulo(),
                e.getDescricao(),
                e.getPrioridade(),
                e.getPrazo(),
                ScoreConfiabilidade.of(e.getScoreConfiabilidade()),
                e.getCreatedAt(),
                e.getUpdatedAt()
        );
    }

    private ProdutoCorrelacao toProdutoCorrelacao(ProdutoCorrelacaoEmbeddable e) {
        return new ProdutoCorrelacao(
                ProdutoId.of(e.getProdutoId()),
                ScoreConfiabilidade.of(e.getScore()),
                e.getCreatedAt()
        );
    }

    private TagCorrelacao toTagCorrelacao(TagCorrelacaoEmbeddable e) {
        return new TagCorrelacao(
                InsightTagId.of(e.getTagId()),
                ScoreConfiabilidade.of(e.getScore()),
                e.getCreatedAt()
        );
    }
}

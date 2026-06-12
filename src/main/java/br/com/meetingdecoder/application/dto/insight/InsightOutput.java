package br.com.meetingdecoder.application.dto.insight;

import br.com.meetingdecoder.domain.insight.model.Insight;
import br.com.meetingdecoder.domain.insight.valueobject.AcaoRecomendada;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record InsightOutput(
        UUID id,
        UUID transcricaoId,
        String polaridade,
        Double scoreSentimento,
        String descricao,
        String trechoOrigem,
        List<String> acoes,
        List<UUID> produtos,
        List<UUID> tags,
        Instant createdAt,
        Instant updatedAt
) {

    public static InsightOutput from(
            Insight insight
    ) {

        return new InsightOutput(
                insight.getId().value(),
                (UUID) insight.getTranscricaoId(),
                insight.getSentimento()
                        .getPolaridade()
                        .name(),
                insight.getSentimento()
                        .getScore()
                        .value(),
                insight.getDescricao(),
                insight.getTrechoOrigem(),

                insight.getAcoes()
                        .stream()
                        .map(AcaoRecomendada::getDescricao)
                        .toList(),

                insight.getProdutos()
                        .stream()
                        .map(produto -> produto.produtoId().value())
                        .toList(),

                insight.getTags()
                        .stream()
                        .map(tag -> tag.tagId().value())
                        .toList(),

                insight.getCreatedAt(),
                insight.getUpdatedAt()
        );
    }
}
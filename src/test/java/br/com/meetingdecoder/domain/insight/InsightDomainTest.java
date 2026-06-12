package br.com.meetingdecoder.domain.insight;

import br.com.meetingdecoder.domain.insight.enums.Polaridade;
import br.com.meetingdecoder.domain.insight.enums.Prioridade;
import br.com.meetingdecoder.domain.insight.model.Insight;
import br.com.meetingdecoder.domain.insight.model.InsightTag;
import br.com.meetingdecoder.domain.insight.model.Produto;
import br.com.meetingdecoder.domain.insight.valueobject.*;
import br.com.meetingdecoder.domain.shared.exception.DomainValidationException;
import br.com.meetingdecoder.domain.transcription.valueobject.TranscriptionId;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class InsightDomainTest {

    @Test
    void shouldClassifyConfidenceScoresAndRejectOutOfRange() {
        assertTrue(ScoreConfiabilidade.of(0.49).isBaixa());
        assertTrue(ScoreConfiabilidade.of(0.50).isMedia());
        assertTrue(ScoreConfiabilidade.of(0.80).isAlta());
        assertThrows(IllegalArgumentException.class, () -> ScoreConfiabilidade.of(1.01));
    }

    @Test
    void shouldIdentifyPotentialChurnOnlyForHighNegativeSentiment() {
        assertTrue(Sentimento.of(Polaridade.NEGATIVO, 0.9).isSinalDeChurnPotencial());
        assertFalse(Sentimento.of(Polaridade.NEGATIVO, 0.6).isSinalDeChurnPotencial());
        assertFalse(Sentimento.of(Polaridade.POSITIVO, 0.9).isSinalDeChurnPotencial());
    }

    @Test
    void shouldCreateImmutableInsightAndUpdateSelectedFields() {
        List<AcaoRecomendada> actions = new ArrayList<>();
        actions.add(AcaoRecomendada.create(
                "Call client", "Schedule follow-up", Prioridade.ALTA,
                null, ScoreConfiabilidade.alta()
        ));

        Insight insight = Insight.create(
                TranscriptionId.of(UUID.randomUUID()),
                Sentimento.of(Polaridade.NEGATIVO, 0.9),
                "Churn risk", "origin", ScoreConfiabilidade.alta(),
                actions, List.of(), List.of()
        );
        actions.clear();

        assertEquals(1, insight.getAcoes().size());
        assertTrue(insight.isSinalDeChurnPotencial());
        assertThrows(UnsupportedOperationException.class, () -> insight.getAcoes().clear());

        Insight updated = insight.update(
                Sentimento.neutro(), "Updated", null, null, null, null, null
        );
        assertEquals(insight.getId(), updated.getId());
        assertEquals("Updated", updated.getDescricao());
        assertTrue(updated.getSentimento().isNeutro());
        assertNotNull(updated.getUpdatedAt());
    }

    @Test
    void shouldRejectInvalidInsightAndRecommendedAction() {
        assertThrows(
                DomainValidationException.class,
                () -> Insight.create(
                        null, null, " ", null, null,
                        null, null, null
                )
        );
        assertThrows(
                DomainValidationException.class,
                () -> AcaoRecomendada.create(
                        " ", "description", Prioridade.BAIXA, Instant.now(),
                        ScoreConfiabilidade.baixa()
                )
        );
    }

    @Test
    void shouldCreateAndUpdateProductWithOptionalPriceRange() {
        Produto produto = Produto.create(
                "Decoder", "Software", "Analytics", "Pro",
                BigDecimal.TEN, BigDecimal.valueOf(100)
        );
        assertEquals(BigDecimal.TEN, produto.faixaPreco().orElseThrow().minimo().orElseThrow());

        Produto updated = produto.update(null, "SaaS", null, null, null, null);
        assertEquals("Decoder", updated.nome());
        assertEquals("SaaS", updated.categoria());
        assertNotNull(updated.updatedAt());

        assertThrows(
                IllegalArgumentException.class,
                () -> Produto.create("Invalid", null, null, null, BigDecimal.TEN, BigDecimal.ONE)
        );
    }

    @Test
    void shouldCreateAndUpdateTagWithoutChangingIdentity() {
        InsightTag tag = InsightTag.create("churn");
        InsightTag updated = tag.update("retention");

        assertEquals(tag.getId(), updated.getId());
        assertEquals(tag.getCreatedAt(), updated.getCreatedAt());
        assertEquals("retention", updated.getNome());
        assertThrows(DomainValidationException.class, () -> tag.update(" "));
    }

    @Test
    void shouldValidateCorrelationComponents() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new ProdutoCorrelacao(null, ScoreConfiabilidade.alta(), Instant.now())
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> new TagCorrelacao(
                        InsightTagId.of(UUID.randomUUID()), null, Instant.now()
                )
        );
    }
}

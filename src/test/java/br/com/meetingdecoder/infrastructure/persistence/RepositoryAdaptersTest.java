package br.com.meetingdecoder.infrastructure.persistence;

import br.com.meetingdecoder.domain.insight.enums.Polaridade;
import br.com.meetingdecoder.domain.insight.valueobject.InsightId;
import br.com.meetingdecoder.domain.insight.valueobject.InsightTagId;
import br.com.meetingdecoder.domain.insight.valueobject.ProdutoId;
import br.com.meetingdecoder.domain.sale.valueobject.ClientId;
import br.com.meetingdecoder.domain.sale.valueobject.SellerId;
import br.com.meetingdecoder.domain.transcription.valueobject.MeetingId;
import br.com.meetingdecoder.domain.transcription.valueobject.TranscriptionId;
import br.com.meetingdecoder.infrastructure.persistence.entity.*;
import br.com.meetingdecoder.infrastructure.persistence.jpa.*;
import br.com.meetingdecoder.infrastructure.persistence.repository.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static br.com.meetingdecoder.support.TestFixtures.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RepositoryAdaptersTest {

    @Test
    void shouldMapClientInBothDirections() {
        JpaClientRepository jpa = mock(JpaClientRepository.class);
        when(jpa.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        ClientRepositoryAdapter adapter = new ClientRepositoryAdapter(jpa);

        var saved = adapter.save(client());

        assertEquals(CLIENT_ID, saved.id());
        assertEquals("Meeting Decoder Ltda", saved.corporateReason());
        assertEquals(9.0, saved.npsNote());
        verify(jpa).save(any(ClientEntity.class));
    }

    @Test
    void shouldMapSellerInBothDirectionsAndDelegateOperations() {
        JpaSellerRepository jpa = mock(JpaSellerRepository.class);
        when(jpa.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(jpa.existsByName("Ana Seller")).thenReturn(true);
        SellerRepositoryAdapter adapter = new SellerRepositoryAdapter(jpa);

        var saved = adapter.save(seller());

        assertEquals(SELLER_ID, saved.id());
        assertEquals("ana@example.com", saved.email());
        assertTrue(adapter.existsByName("Ana Seller"));
        adapter.deleteById(SellerId.of(SELLER_ID));
        verify(jpa).deleteById(SELLER_ID);
    }

    @Test
    void shouldMapMeetingInBothDirections() {
        JpaMeetingRepository jpa = mock(JpaMeetingRepository.class);
        when(jpa.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        MeetingRepositoryAdapter adapter = new MeetingRepositoryAdapter(jpa);

        var saved = adapter.save(meeting());

        assertEquals(MEETING_ID, saved.id().value());
        assertEquals(3_600, saved.durationInSeconds());
        assertEquals(SELLER_ID, saved.participants().sellerId().value());
    }

    @Test
    void shouldMapTranscriptionInBothDirections() {
        JpaTranscriptionRepository jpa = mock(JpaTranscriptionRepository.class);
        when(jpa.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        TranscriptionRepositoryAdapter adapter = new TranscriptionRepositoryAdapter(jpa);

        var saved = adapter.save(transcription());

        assertEquals(TRANSCRIPTION_ID, saved.id().value());
        assertEquals(0.91, saved.modelConfidence().value());
        assertFalse(adapter.existsByName("unused"));
    }

    @Test
    void shouldMapProductQueries() {
        JpaProdutoRepository jpa = mock(JpaProdutoRepository.class);
        ProdutoEntity entity = ProdutoEntity.builder()
                .id(PRODUCT_ID).nome("Decoder Pro").categoria("Software")
                .descricao("Analytics").linha("Enterprise")
                .faixaPrecoMinimo(BigDecimal.TEN).faixaPrecoMaximo(BigDecimal.valueOf(100))
                .createdAt(Instant.parse("2025-01-15T12:00:00Z"))
                .build();
        when(jpa.findById(PRODUCT_ID)).thenReturn(Optional.of(entity));
        when(jpa.findAllByCategoria("Software")).thenReturn(List.of(entity));
        ProdutoRepositoryAdapter adapter = new ProdutoRepositoryAdapter(jpa);

        assertEquals("Decoder Pro", adapter.findById(ProdutoId.of(PRODUCT_ID)).orElseThrow().nome());
        assertEquals(1, adapter.findByCategoria("Software").size());
    }

    @Test
    void shouldMapInsightTagQueries() {
        JpaInsightTagRepository jpa = mock(JpaInsightTagRepository.class);
        InsightTagEntity entity = InsightTagEntity.builder()
                .id(TAG_ID).nome("churn")
                .createdAt(Instant.parse("2025-01-15T12:00:00Z"))
                .build();
        when(jpa.findAll()).thenReturn(List.of(entity));
        when(jpa.findByNome("churn")).thenReturn(Optional.of(entity));
        InsightTagRepositoryAdapter adapter = new InsightTagRepositoryAdapter(jpa);

        assertEquals("churn", adapter.findByNome("churn").orElseThrow().getNome());
        assertEquals(TAG_ID, adapter.findAll().getFirst().getId().value());
    }

    @Test
    void shouldMapInsightsAndUseBusinessQueryThresholds() {
        JpaInsightRepository jpa = mock(JpaInsightRepository.class);
        InsightEntity entity = InsightEntity.builder()
                .id(INSIGHT_ID)
                .transcricaoId(TRANSCRIPTION_ID)
                .polaridade(Polaridade.NEGATIVO)
                .sentimentoScore(0.90)
                .descricao("Churn risk")
                .trechoOrigem("origin")
                .scoreConfiabilidade(0.85)
                .createdAt(Instant.parse("2025-01-15T12:00:00Z"))
                .build();
        when(jpa.findById(INSIGHT_ID)).thenReturn(Optional.of(entity));
        when(jpa.findRiscosChurnAtivos(CLIENT_ID, Polaridade.NEGATIVO, 0.80))
                .thenReturn(List.of(entity));
        when(jpa.findInsightsPrioritariosParaVendedor(SELLER_ID, 0.50))
                .thenReturn(List.of(entity));
        InsightRepositoryAdapter adapter = new InsightRepositoryAdapter(jpa);

        assertEquals(INSIGHT_ID, adapter.findById(InsightId.of(INSIGHT_ID)).orElseThrow().getId().value());
        assertEquals(1, adapter.findRiscosChurnAtivos(ClientId.of(CLIENT_ID)).size());
        assertEquals(1, adapter.findInsightsPrioritariosParaVendedor(SellerId.of(SELLER_ID)).size());
        verify(jpa).findRiscosChurnAtivos(CLIENT_ID, Polaridade.NEGATIVO, 0.80);
        verify(jpa).findInsightsPrioritariosParaVendedor(SELLER_ID, 0.50);
    }
}

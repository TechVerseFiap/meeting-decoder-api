package br.com.meetingdecoder.infrastructure.persistence.jpa;

import br.com.meetingdecoder.domain.insight.enums.Polaridade;
import br.com.meetingdecoder.infrastructure.persistence.entity.InsightEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface JpaInsightRepository extends JpaRepository<InsightEntity, UUID> {

    List<InsightEntity> findAllByTranscricaoId(UUID transcricaoId);

    @Query("""
            SELECT i FROM InsightEntity i
            WHERE i.polaridade = :polaridade
              AND i.sentimentoScore >= :scoreThreshold
              AND i.transcricaoId IN (
                  SELECT t.id FROM TranscriptionEntity t
                  WHERE t.meetingId IN (
                      SELECT m.id FROM MeetingEntity m
                      WHERE m.clientId = :clientId
                  )
              )
            """)
    List<InsightEntity> findRiscosChurnAtivos(
            @Param("clientId") UUID clientId,
            @Param("polaridade") Polaridade polaridade,
            @Param("scoreThreshold") double scoreThreshold
    );

    @Query("""
            SELECT i FROM InsightEntity i
            WHERE i.scoreConfiabilidade >= :scoreThreshold
              AND i.transcricaoId IN (
                  SELECT t.id FROM TranscriptionEntity t
                  WHERE t.meetingId IN (
                      SELECT m.id FROM MeetingEntity m
                      WHERE m.sellerId = :sellerId
                  )
              )
            ORDER BY i.scoreConfiabilidade DESC
            """)
    List<InsightEntity> findInsightsPrioritariosParaVendedor(
            @Param("sellerId") UUID sellerId,
            @Param("scoreThreshold") double scoreThreshold
    );
}

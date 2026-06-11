package br.com.meetingdecoder.domain.insight.repository;

import br.com.meetingdecoder.domain.insight.model.Insight;
import br.com.meetingdecoder.domain.insight.valueobject.InsightId;

import java.util.List;
import java.util.Optional;

/**
 * Domain port for reading and querying insights from the repository.
 * Methods are named for the dashboard use cases they serve, not for the SQL operations they require.
 * 
 * Note: Cross-context ID references are passed as Object (TranscricaoId, ClienteId, VendedorId)
 * because those types are defined in other bounded contexts. Implementations should cast
 * appropriately or be provided by the infra layer as UUIDs.
 */
public interface IInsightRepository {
    /**
     * Finds an insight by its unique identifier.
     *
     * @param id the insight identifier
     * @return the insight if found
     */
    Optional<Insight> findById(InsightId id);

    /**
     * Finds all insights related to a specific transcription.
     * Dashboard use case: display all insights extracted from a meeting transcription.
     *
     * @param transcricaoId the transcription identifier (type: TranscricaoId from Transcricao context)
     * @return a list of insights for that transcription (may be empty)
     */
    List<Insight> findByTranscricaoId(Object transcricaoId);

    /**
     * Finds all churn risk signals (unresolved) for a client's meetings.
     * Dashboard use case: sales managers view active churn signals to prioritize customer interventions.
     * 
     * An insight is a churn signal when sentiment is NEGATIVO with high confidence (≥ 0.80).
     *
     * @param clienteId the client identifier (type: ClienteId from Cliente context)
     * @return a list of active churn-signal insights for that client (may be empty)
     */
    List<Insight> findRiscosChurnAtivos(Object clienteId);

    /**
     * Finds high-confidence insights relevant to a sales rep's action panel.
     * Dashboard use case: vendors/sales reps view prioritized insights with high-confidence recommendations.
     * 
     * "High-confidence" typically means scoreConfiabilidade ≥ 0.80 and/or related actions with
     * ALTA or CRITICA priority.
     *
     * @param vendedorId the sales rep/vendor identifier (type: VendedorId from Vendedor context)
     * @return a list of prioritized insights for that vendor (may be empty)
     */
    List<Insight> findInsightsPrioritariosParaVendedor(Object vendedorId);
}

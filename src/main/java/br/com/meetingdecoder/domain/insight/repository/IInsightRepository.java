package br.com.meetingdecoder.domain.insight.repository;

import br.com.meetingdecoder.domain.insight.model.Insight;
import br.com.meetingdecoder.domain.insight.valueobject.InsightId;

import java.util.List;
import java.util.Optional;

public interface IInsightRepository {
    Optional<Insight> findById(InsightId id);

    List<Insight> findByTranscricaoId(Object transcricaoId);

    List<Insight> findRiscosChurnAtivos(Object clienteId);

    List<Insight> findInsightsPrioritariosParaVendedor(Object vendedorId);
}

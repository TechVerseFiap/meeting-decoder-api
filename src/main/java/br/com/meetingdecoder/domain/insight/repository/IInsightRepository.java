package br.com.meetingdecoder.domain.insight.repository;

import br.com.meetingdecoder.domain.insight.model.Insight;
import br.com.meetingdecoder.domain.insight.valueobject.InsightId;
import br.com.meetingdecoder.domain.sale.valueobject.ClientId;
import br.com.meetingdecoder.domain.sale.valueobject.SellerId;
import br.com.meetingdecoder.domain.transcription.valueobject.TranscriptionId;

import java.util.List;
import java.util.Optional;

public interface IInsightRepository {
    Optional<Insight> findById(InsightId id);

    List<Insight> findByTranscricaoId(TranscriptionId transcricaoId);

    List<Insight> findRiscosChurnAtivos(ClientId clienteId);

    List<Insight> findInsightsPrioritariosParaVendedor(SellerId vendedorId);
}

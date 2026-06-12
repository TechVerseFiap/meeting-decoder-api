package br.com.meetingdecoder.application.service.insight;

import br.com.meetingdecoder.application.dto.insight.InsightOutput;
import br.com.meetingdecoder.application.ports.insight.IFindActiveChurnRisksUseCase;
import br.com.meetingdecoder.application.shared.result.Result;
import br.com.meetingdecoder.domain.insight.repository.IInsightRepository;
import br.com.meetingdecoder.domain.sale.valueobject.ClientId;

import java.util.List;
import java.util.UUID;

public class FindRiscosChurnAtivosUseCase
        implements IFindActiveChurnRisksUseCase {

    private final IInsightRepository repository;

    public FindRiscosChurnAtivosUseCase(
            IInsightRepository repository
    ) {
        this.repository = repository;
    }

    @Override
    public Result<List<InsightOutput>> execute(
            UUID clienteId
    ) {
        List<InsightOutput> insights =
                repository.findRiscosChurnAtivos(ClientId.of(clienteId))
                        .stream()
                        .map(InsightOutput::from)
                        .toList();

        return Result.success(insights);
    }
}

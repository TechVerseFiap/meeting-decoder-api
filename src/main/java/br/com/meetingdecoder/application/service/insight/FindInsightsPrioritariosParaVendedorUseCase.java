package br.com.meetingdecoder.application.service.insight;

import br.com.meetingdecoder.application.dto.insight.InsightOutput;
import br.com.meetingdecoder.application.ports.insight.IFindPriorityInsightsForSellerUseCase;
import br.com.meetingdecoder.application.shared.result.Result;
import br.com.meetingdecoder.domain.insight.repository.IInsightRepository;
import br.com.meetingdecoder.domain.sale.valueobject.SellerId;

import java.util.List;
import java.util.UUID;

public class FindInsightsPrioritariosParaVendedorUseCase
        implements IFindPriorityInsightsForSellerUseCase {

    private final IInsightRepository repository;

    public FindInsightsPrioritariosParaVendedorUseCase(
            IInsightRepository repository
    ) {
        this.repository = repository;
    }

    @Override
    public Result<List<InsightOutput>> execute(
            UUID vendedorId
    ) {
        List<InsightOutput> insights =
                repository.findInsightsPrioritariosParaVendedor(SellerId.of(vendedorId))
                        .stream()
                        .map(InsightOutput::from)
                        .toList();

        return Result.success(insights);
    }
}
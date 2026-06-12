package br.com.meetingdecoder.application.service.insight;

import br.com.meetingdecoder.application.dto.insight.InsightOutput;
import br.com.meetingdecoder.application.ports.insight.IFindInsightByIdUseCase;
import br.com.meetingdecoder.application.shared.result.Result;
import br.com.meetingdecoder.domain.insight.model.Insight;
import br.com.meetingdecoder.domain.insight.repository.IInsightRepository;
import br.com.meetingdecoder.domain.insight.valueobject.InsightId;
import br.com.meetingdecoder.domain.shared.validation.DomainError;
import br.com.meetingdecoder.domain.shared.validation.DomainErrorCode;

import java.util.Optional;
import java.util.UUID;

public class FindInsightByIdUseCase
        implements IFindInsightByIdUseCase {

    private final IInsightRepository repository;

    public FindInsightByIdUseCase(
            IInsightRepository repository
    ) {
        this.repository = repository;
    }

    @Override
    public Result<InsightOutput> execute(
            UUID insightId
    ) {
        Optional<Insight> insight =
                repository.findById(
                        InsightId.of(insightId)
                );

        return insight.map(value ->
                Result.success(
                        InsightOutput.from(value)
                )
        ).orElseGet(() ->
                Result.failure(
                        DomainError.of(
                                DomainErrorCode.NOT_FOUND,
                                "Insight",
                                insightId.toString()
                        )
                )
        );
    }
}

package br.com.meetingdecoder.application.service.insighttag;

import br.com.meetingdecoder.application.dto.insighttag.InsightTagOutput;
import br.com.meetingdecoder.application.ports.insighttag.IFindInsightTagByIdUseCase;
import br.com.meetingdecoder.application.shared.result.Result;
import br.com.meetingdecoder.domain.insight.model.InsightTag;
import br.com.meetingdecoder.domain.insight.repository.IInsightTagRepository;
import br.com.meetingdecoder.domain.insight.valueobject.InsightTagId;
import br.com.meetingdecoder.domain.shared.validation.DomainError;
import br.com.meetingdecoder.domain.shared.validation.DomainErrorCode;

import java.util.Optional;
import java.util.UUID;

public class FindInsightTagByIdUseCase
        implements IFindInsightTagByIdUseCase {

    private final IInsightTagRepository repository;

    public FindInsightTagByIdUseCase(
            IInsightTagRepository repository
    ) {
        this.repository = repository;
    }

    @Override
    public Result<InsightTagOutput> execute(
            UUID tagId
    ) {
        Optional<InsightTag> tag =
                repository.findById(
                        InsightTagId.of(tagId)
                );

        return tag.map(value ->
                Result.success(
                        InsightTagOutput.from(value)
                )
        ).orElseGet(() ->
                Result.failure(
                        DomainError.of(
                                DomainErrorCode.NOT_FOUND,
                                "InsightTag",
                                tagId.toString()
                        )
                )
        );
    }
}

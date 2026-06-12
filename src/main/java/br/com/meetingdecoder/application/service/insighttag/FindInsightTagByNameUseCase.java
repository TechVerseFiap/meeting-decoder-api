package br.com.meetingdecoder.application.service.insighttag;

import br.com.meetingdecoder.application.dto.insighttag.InsightTagOutput;
import br.com.meetingdecoder.application.ports.insighttag.IFindInsightTagByNameUseCase;
import br.com.meetingdecoder.application.shared.result.Result;
import br.com.meetingdecoder.domain.insight.model.InsightTag;
import br.com.meetingdecoder.domain.insight.repository.IInsightTagRepository;
import br.com.meetingdecoder.domain.shared.validation.DomainError;
import br.com.meetingdecoder.domain.shared.validation.DomainErrorCode;

import java.util.Optional;

public class FindInsightTagByNameUseCase
        implements IFindInsightTagByNameUseCase {

    private final IInsightTagRepository repository;

    public FindInsightTagByNameUseCase(
            IInsightTagRepository repository
    ) {
        this.repository = repository;
    }

    @Override
    public Result<InsightTagOutput> execute(
            String nome
    ) {
        Optional<InsightTag> tag =
                repository.findByNome(nome);

        return tag.map(value ->
                Result.success(
                        InsightTagOutput.from(value)
                )
        ).orElseGet(() ->
                Result.failure(
                        DomainError.of(
                                DomainErrorCode.NOT_FOUND,
                                "InsightTag",
                                nome
                        )
                )
        );
    }
}

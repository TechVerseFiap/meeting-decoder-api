package br.com.meetingdecoder.application.service.insighttag;

import br.com.meetingdecoder.application.dto.insighttag.InsightTagOutput;
import br.com.meetingdecoder.application.ports.insighttag.IListInsightTagsUseCase;
import br.com.meetingdecoder.application.shared.result.Result;
import br.com.meetingdecoder.domain.insight.repository.IInsightTagRepository;

import java.util.List;

public class ListInsightTagsUseCase
        implements IListInsightTagsUseCase {

    private final IInsightTagRepository repository;

    public ListInsightTagsUseCase(
            IInsightTagRepository repository
    ) {
        this.repository = repository;
    }

    @Override
    public Result<List<InsightTagOutput>> execute() {

        List<InsightTagOutput> tags =
                repository.findAll()
                        .stream()
                        .map(InsightTagOutput::from)
                        .toList();

        return Result.success(tags);
    }
}

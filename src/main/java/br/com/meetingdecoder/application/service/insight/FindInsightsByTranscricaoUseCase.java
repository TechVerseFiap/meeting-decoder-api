package br.com.meetingdecoder.application.service.insight;

import br.com.meetingdecoder.application.dto.insight.InsightOutput;
import br.com.meetingdecoder.application.ports.insight.IFindInsightsByTranscriptionUseCase;
import br.com.meetingdecoder.application.shared.result.Result;
import br.com.meetingdecoder.domain.insight.repository.IInsightRepository;

import java.util.List;
import java.util.UUID;

public class FindInsightsByTranscricaoUseCase
        implements IFindInsightsByTranscriptionUseCase {

    private final IInsightRepository repository;

    public FindInsightsByTranscricaoUseCase(
            IInsightRepository repository
    ) {
        this.repository = repository;
    }

    @Override
    public Result<List<InsightOutput>> execute(
            UUID transcricaoId
    ) {
        List<InsightOutput> insights =
                repository.findByTranscricaoId(transcricaoId)
                        .stream()
                        .map(InsightOutput::from)
                        .toList();

        return Result.success(insights);
    }
}

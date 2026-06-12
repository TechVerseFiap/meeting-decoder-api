package br.com.meetingdecoder.application.ports.insight;

import br.com.meetingdecoder.application.dto.insight.InsightOutput;
import br.com.meetingdecoder.application.shared.result.Result;

import java.util.UUID;

public interface IFindInsightByIdUseCase {
    Result<InsightOutput> execute(UUID insightId);
}

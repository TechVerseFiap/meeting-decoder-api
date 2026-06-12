package br.com.meetingdecoder.application.ports.insight;

import br.com.meetingdecoder.application.dto.insight.InsightOutput;
import br.com.meetingdecoder.application.shared.result.Result;

import java.util.List;
import java.util.UUID;

public interface IFindPriorityInsightsForSellerUseCase {
    Result<List<InsightOutput>> execute(UUID sellerId);
}

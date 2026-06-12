package br.com.meetingdecoder.application.ports.insighttag;

import br.com.meetingdecoder.application.dto.insighttag.InsightTagOutput;
import br.com.meetingdecoder.application.shared.result.Result;

import java.util.UUID;

public interface IFindInsightTagByIdUseCase {
    Result<InsightTagOutput> execute(UUID tagId);
}

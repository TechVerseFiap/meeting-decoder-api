package br.com.meetingdecoder.application.ports.insighttag;

import br.com.meetingdecoder.application.dto.insighttag.InsightTagOutput;
import br.com.meetingdecoder.application.shared.result.Result;

public interface IFindInsightTagByNameUseCase {
    Result<InsightTagOutput> execute(String nome);
}

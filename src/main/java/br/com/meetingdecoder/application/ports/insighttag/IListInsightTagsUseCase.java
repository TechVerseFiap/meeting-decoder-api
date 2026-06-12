package br.com.meetingdecoder.application.ports.insighttag;

import br.com.meetingdecoder.application.dto.insighttag.InsightTagOutput;
import br.com.meetingdecoder.application.shared.result.Result;

import java.util.List;

public interface IListInsightTagsUseCase {
    Result<List<InsightTagOutput>> execute();
}
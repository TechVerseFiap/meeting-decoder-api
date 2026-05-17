package br.com.meetingdecoder.application.ports.in.serviceRecommendation;

import br.com.meetingdecoder.application.dto.ServiceRecommendationResponse;
import br.com.meetingdecoder.application.shared.enums.SortDirection;
import br.com.meetingdecoder.application.shared.result.Result;

import java.util.List;

public interface ListServiceRecommendationUseCase {
    Result<List<ServiceRecommendationResponse>> all();
    Result<List<ServiceRecommendationResponse>> orderedBy(SortDirection direction);
}

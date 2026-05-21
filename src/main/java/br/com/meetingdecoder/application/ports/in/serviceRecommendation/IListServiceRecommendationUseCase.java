package br.com.meetingdecoder.application.ports.in.serviceRecommendation;

import br.com.meetingdecoder.application.dto.QueryOptions;
import br.com.meetingdecoder.application.dto.ServiceRecommendationResponse;
import br.com.meetingdecoder.application.shared.result.Result;

import java.util.List;

public interface IListServiceRecommendationUseCase {
    Result<List<ServiceRecommendationResponse>> all(QueryOptions queryOptions);
}

package br.com.meetingdecoder.application.ports.in.serviceRecommendation;

import br.com.meetingdecoder.application.dto.ServiceRecommendationResponse;
import br.com.meetingdecoder.application.shared.result.Result;

import java.util.UUID;

public interface IGetServiceRecommendationUseCase {
    Result<ServiceRecommendationResponse> byId(UUID id);
}

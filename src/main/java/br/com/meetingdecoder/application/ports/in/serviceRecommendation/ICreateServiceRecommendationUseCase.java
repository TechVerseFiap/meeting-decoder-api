package br.com.meetingdecoder.application.ports.in.serviceRecommendation;

import br.com.meetingdecoder.application.command.serviceRecommendation.CreateServiceRecommendationCommand;
import br.com.meetingdecoder.application.dto.ServiceRecommendationResponse;
import br.com.meetingdecoder.application.shared.result.Result;

public interface ICreateServiceRecommendationUseCase {
    Result<ServiceRecommendationResponse> execute(CreateServiceRecommendationCommand payload);
}

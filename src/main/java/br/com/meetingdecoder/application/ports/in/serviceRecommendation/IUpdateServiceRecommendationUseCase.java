package br.com.meetingdecoder.application.ports.in.serviceRecommendation;

import br.com.meetingdecoder.application.command.serviceRecommendation.UpdateServiceRecommendationCommand;
import br.com.meetingdecoder.application.dto.ServiceRecommendationResponse;
import br.com.meetingdecoder.application.shared.result.Result;

import java.util.UUID;

public interface IUpdateServiceRecommendationUseCase {
    Result<ServiceRecommendationResponse> execute(UUID id, UpdateServiceRecommendationCommand payload);
}

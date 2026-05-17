package br.com.meetingdecoder.application.ports.in;

import br.com.meetingdecoder.application.command.serviceRecommendation.CreateServiceRecommendationCommand;
import br.com.meetingdecoder.application.command.serviceRecommendation.UpdateServiceRecommendationCommand;
import br.com.meetingdecoder.application.dto.ServiceRecommendationResponse;
import br.com.meetingdecoder.application.shared.enums.SortDirection;
import br.com.meetingdecoder.application.shared.result.Result;

import java.util.List;
import java.util.UUID;

public interface ServiceRecommendationUseCase {
    Result<ServiceRecommendationResponse> createNewService(CreateServiceRecommendationCommand payload);

    Result<ServiceRecommendationResponse> getById(UUID id);
    Result<ServiceRecommendationResponse> getByName(String name);

    Result<List<ServiceRecommendationResponse>> listAllServices();
    Result<List<ServiceRecommendationResponse>> listAllServicesOrderBy(SortDirection direction);

    Result<ServiceRecommendationResponse> update(UUID id, UpdateServiceRecommendationCommand payload);

    Result<Void> deleteById(UUID id);
    Result<Void> deleteByName(String name);
}

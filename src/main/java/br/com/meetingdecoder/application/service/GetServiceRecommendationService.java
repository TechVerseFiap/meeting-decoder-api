package br.com.meetingdecoder.application.service;

import br.com.meetingdecoder.application.dto.ServiceRecommendationResponse;
import br.com.meetingdecoder.application.ports.in.serviceRecommendation.GetServiceRecommendationUseCase;
import br.com.meetingdecoder.application.ports.out.ServiceRecommendationRepository;
import br.com.meetingdecoder.application.shared.error.DomainErrorFactory;
import br.com.meetingdecoder.application.shared.result.Result;
import br.com.meetingdecoder.domain.model.ServiceRecommendation;

import java.util.Optional;
import java.util.UUID;

public class GetServiceRecommendationService implements GetServiceRecommendationUseCase {

    private final ServiceRecommendationRepository serviceRecommendationRepository;

    public GetServiceRecommendationService (ServiceRecommendationRepository serviceRecommendationRepository) {
        this.serviceRecommendationRepository = serviceRecommendationRepository;
    }

    @Override
    public Result<ServiceRecommendationResponse> byId(UUID id) {
        Optional<ServiceRecommendation> serviceRecommendation = serviceRecommendationRepository.findById(id);
        if (serviceRecommendation.isEmpty()) {
            return Result.failure(DomainErrorFactory.notFound("service recommendation"));
        }
        ServiceRecommendationResponse response = ServiceRecommendationResponse.from(serviceRecommendation.get());
        return Result.success(response);
    }
}

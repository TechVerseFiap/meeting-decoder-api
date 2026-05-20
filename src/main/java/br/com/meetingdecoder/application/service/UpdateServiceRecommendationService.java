package br.com.meetingdecoder.application.service;

import br.com.meetingdecoder.application.command.serviceRecommendation.UpdateServiceRecommendationCommand;
import br.com.meetingdecoder.application.dto.ServiceRecommendationResponse;
import br.com.meetingdecoder.application.ports.in.serviceRecommendation.UpdateServiceRecommendationUseCase;
import br.com.meetingdecoder.application.ports.out.ServiceRecommendationRepository;
import br.com.meetingdecoder.application.shared.error.DomainErrorFactory;
import br.com.meetingdecoder.application.shared.result.Result;
import br.com.meetingdecoder.domain.model.ServiceRecommendation;

import java.util.Optional;
import java.util.UUID;

public class UpdateServiceRecommendationService implements UpdateServiceRecommendationUseCase {

    private final ServiceRecommendationRepository serviceRecommendationRepository;

    public UpdateServiceRecommendationService (ServiceRecommendationRepository serviceRecommendationRepository) {
        this.serviceRecommendationRepository = serviceRecommendationRepository;
    }

    @Override
    public Result<ServiceRecommendationResponse> execute(UUID id, UpdateServiceRecommendationCommand payload) {
        Optional<ServiceRecommendation> foundRecommendation = serviceRecommendationRepository.findById(id);
        if (foundRecommendation.isEmpty()) {
            return Result.failure(DomainErrorFactory.notFound("service recommendation not found"));
        }
        ServiceRecommendation serviceRecommendation = foundRecommendation.get();
        serviceRecommendation.update(
                payload.name(),
                payload.category(),
                payload.description(),
                payload.price()
        );
        ServiceRecommendationResponse response = ServiceRecommendationResponse.from(
                serviceRecommendationRepository.save(serviceRecommendation)
        );
        return Result.success(response);
    }
}

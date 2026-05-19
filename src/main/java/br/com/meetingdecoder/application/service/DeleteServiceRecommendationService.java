package br.com.meetingdecoder.application.service;

import br.com.meetingdecoder.application.ports.in.serviceRecommendation.DeleteServiceRecommendationUseCase;
import br.com.meetingdecoder.application.ports.out.ServiceRecommendationRepository;
import br.com.meetingdecoder.application.shared.error.DomainError;
import br.com.meetingdecoder.application.shared.error.DomainErrorFactory;
import br.com.meetingdecoder.application.shared.result.Result;

import java.util.UUID;

public class DeleteServiceRecommendationService implements DeleteServiceRecommendationUseCase {

    private final ServiceRecommendationRepository serviceRecommendationRepository;

    public DeleteServiceRecommendationService (ServiceRecommendationRepository serviceRecommendationRepository) {
        this.serviceRecommendationRepository = serviceRecommendationRepository;
    }

    @Override
    public Result<Void> byId(UUID id) {
        if (!serviceRecommendationRepository.existsById(id)) {
            return Result.failure(DomainErrorFactory.notFound("service recommendation"));
        }
        serviceRecommendationRepository.deleteById(id);
        return Result.success();
    }
}

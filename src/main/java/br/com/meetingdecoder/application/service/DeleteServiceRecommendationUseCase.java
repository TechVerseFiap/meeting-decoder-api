package br.com.meetingdecoder.application.service;

import br.com.meetingdecoder.application.ports.in.serviceRecommendation.IDeleteServiceRecommendationUseCase;
import br.com.meetingdecoder.application.ports.out.IServiceRecommendationRepository;
import br.com.meetingdecoder.application.shared.error.DomainErrorFactory;
import br.com.meetingdecoder.application.shared.result.Result;

import java.util.UUID;

public class DeleteServiceRecommendationUseCase implements IDeleteServiceRecommendationUseCase {

    private final IServiceRecommendationRepository serviceRecommendationRepository;

    public DeleteServiceRecommendationUseCase(IServiceRecommendationRepository serviceRecommendationRepository) {
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

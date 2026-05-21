package br.com.meetingdecoder.application.service;

import br.com.meetingdecoder.application.command.serviceRecommendation.CreateServiceRecommendationCommand;
import br.com.meetingdecoder.application.dto.ServiceRecommendationResponse;
import br.com.meetingdecoder.application.ports.in.serviceRecommendation.ICreateServiceRecommendationUseCase;
import br.com.meetingdecoder.application.ports.out.IServiceRecommendationRepository;
import br.com.meetingdecoder.application.shared.error.DomainErrorFactory;
import br.com.meetingdecoder.application.shared.result.Result;
import br.com.meetingdecoder.domain.model.ServiceRecommendation;

public class CreateServiceRecommendationUseCase implements ICreateServiceRecommendationUseCase {
    private final IServiceRecommendationRepository serviceRecommendationRepository;

    public CreateServiceRecommendationUseCase(IServiceRecommendationRepository serviceRecommendationRepository) {
        this.serviceRecommendationRepository = serviceRecommendationRepository;
    }

    @Override
    public Result<ServiceRecommendationResponse> execute(CreateServiceRecommendationCommand payload) {
        boolean alreadyExists = serviceRecommendationRepository.existsByName(payload.name());
        if (alreadyExists) {
            return Result.failure(DomainErrorFactory.alreadyExists("name"));
        }
        ServiceRecommendation serviceRecommendation = ServiceRecommendation.create(
            payload.name(), 
            payload.category(), 
            payload.description(), 
            payload.price()
        );
        ServiceRecommendation saved = serviceRecommendationRepository.save(serviceRecommendation);
        ServiceRecommendationResponse response = ServiceRecommendationResponse.from(saved);
        return Result.success(response);
    }
}
package br.com.meetingdecoder.application.service;

import br.com.meetingdecoder.application.command.serviceRecommendation.CreateServiceRecommendationCommand;
import br.com.meetingdecoder.application.dto.ServiceRecommendationResponse;
import br.com.meetingdecoder.application.ports.in.serviceRecommendation.CreateServiceRecommendationUseCase;
import br.com.meetingdecoder.application.ports.out.ServiceRecommendationRepository;
import br.com.meetingdecoder.application.shared.error.DomainErrorFactory;
import br.com.meetingdecoder.application.shared.error.ErrorAccumulator;
import br.com.meetingdecoder.application.shared.result.Result;
import br.com.meetingdecoder.domain.model.ServiceRecommendation;

public class CreateServiceRecommendationService implements CreateServiceRecommendationUseCase {
    private final ServiceRecommendationRepository serviceRecommendationRepository;

    public CreateServiceRecommendationService (ServiceRecommendationRepository serviceRecommendationRepository) {
        this.serviceRecommendationRepository = serviceRecommendationRepository;
    }

    @Override
    public Result<ServiceRecommendationResponse> execute(CreateServiceRecommendationCommand payload) {
        boolean alreadyExists = serviceRecommendationRepository.existsByName(payload.name());
        
        if (alreadyExists) {
            ErrorAccumulator errors = new ErrorAccumulator();
            errors.add(DomainErrorFactory.alreadyExists("name"));
            return Result.failure(errors);
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
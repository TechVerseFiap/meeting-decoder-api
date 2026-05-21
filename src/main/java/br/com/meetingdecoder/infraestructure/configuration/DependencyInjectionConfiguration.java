package br.com.meetingdecoder.infraestructure.configuration;

import br.com.meetingdecoder.application.ports.in.serviceRecommendation.*;
import br.com.meetingdecoder.application.ports.out.IServiceRecommendationRepository;
import br.com.meetingdecoder.application.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DependencyInjectionConfiguration {

    @Bean
    public ICreateServiceRecommendationUseCase createServiceRecommendationUseCase(
            IServiceRecommendationRepository serviceRecommendationRepository
    ) {
        return new CreateServiceRecommendationUseCase(serviceRecommendationRepository);
    }

    @Bean
    public IGetServiceRecommendationUseCase getServiceRecommendationUseCase(
            IServiceRecommendationRepository serviceRecommendationRepository
    ) {
        return new GetServiceRecommendationUseCase(serviceRecommendationRepository);
    }

    @Bean
    public IListServiceRecommendationUseCase listServiceRecommendationUseCase(
            IServiceRecommendationRepository serviceRecommendationRepository
    ) {
        return new ListServiceRecommendationUseCase(serviceRecommendationRepository);
    }

    @Bean
    public IUpdateServiceRecommendationUseCase updateServiceRecommendationUseCase(
            IServiceRecommendationRepository serviceRecommendationRepository
    ) {
        return new UpdateServiceRecommendationUseCase(serviceRecommendationRepository);
    }

    @Bean
    public IDeleteServiceRecommendationUseCase deleteServiceRecommendationUseCase(
            IServiceRecommendationRepository serviceRecommendationRepository
    ) {
        return new DeleteServiceRecommendationUseCase(serviceRecommendationRepository);
    }
}

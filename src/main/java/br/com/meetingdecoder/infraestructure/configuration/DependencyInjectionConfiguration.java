package br.com.meetingdecoder.infraestructure.configuration;

import br.com.meetingdecoder.application.ports.in.serviceRecommendation.*;
import br.com.meetingdecoder.domain.insight.repository.IProductRepository;
import br.com.meetingdecoder.application.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DependencyInjectionConfiguration {

    @Bean
    public ICreateProductUseCase createServiceRecommendationUseCase(
            IProductRepository productRepository
    ) {
        return new CreateProductUseCase(productRepository);
    }

    @Bean
    public IGetProductUseCase getServiceRecommendationUseCase(
            IProductRepository productRepository
    ) {
        return new GetProductUseCase(productRepository);
    }

    @Bean
    public IListProductUseCase listServiceRecommendationUseCase(
            IProductRepository productRepository
    ) {
        return new ListProductUseCase(productRepository);
    }

    @Bean
    public IUpdateProductUseCase updateServiceRecommendationUseCase(
            IProductRepository productRepository
    ) {
        return new UpdateProductUseCase(productRepository);
    }

    @Bean
    public IDeleteProductUseCase deleteServiceRecommendationUseCase(
            IProductRepository productRepository
    ) {
        return new DeleteProductUseCase(productRepository);
    }
}

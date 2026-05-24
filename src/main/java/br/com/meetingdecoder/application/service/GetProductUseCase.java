package br.com.meetingdecoder.application.service;

import br.com.meetingdecoder.application.dto.ProductResponse;
import br.com.meetingdecoder.application.ports.in.serviceRecommendation.IGetProductUseCase;
import br.com.meetingdecoder.domain.insight.repository.IProductRepository;
import br.com.meetingdecoder.application.shared.error.DomainErrorFactory;
import br.com.meetingdecoder.application.shared.result.Result;
import br.com.meetingdecoder.domain.insight.model.Product;

import java.util.Optional;
import java.util.UUID;

public class GetProductUseCase implements IGetProductUseCase {

    private final IProductRepository productRepository;

    public GetProductUseCase(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Result<ProductResponse> byId(UUID id) {
        Optional<Product> serviceRecommendation = productRepository.findById(id);
        if (serviceRecommendation.isEmpty()) {
            return Result.failure(DomainErrorFactory.notFound("product"));
        }
        ProductResponse response = ProductResponse.from(serviceRecommendation.get());
        return Result.success(response);
    }
}

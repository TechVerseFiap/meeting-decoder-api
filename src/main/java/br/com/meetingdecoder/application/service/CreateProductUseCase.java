package br.com.meetingdecoder.application.service;

import br.com.meetingdecoder.application.command.serviceRecommendation.CreateProductCommand;
import br.com.meetingdecoder.application.dto.ProductResponse;
import br.com.meetingdecoder.application.ports.in.serviceRecommendation.ICreateProductUseCase;
import br.com.meetingdecoder.domain.insight.repository.IProductRepository;
import br.com.meetingdecoder.application.shared.error.DomainErrorFactory;
import br.com.meetingdecoder.application.shared.result.Result;
import br.com.meetingdecoder.domain.insight.model.Product;

public class CreateProductUseCase implements ICreateProductUseCase {
    private final IProductRepository productRepository;

    public CreateProductUseCase(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Result<ProductResponse> execute(CreateProductCommand payload) {
        boolean alreadyExists = productRepository.existsByName(payload.name());
        if (alreadyExists) {
            return Result.failure(DomainErrorFactory.alreadyExists("name"));
        }
        Product product = Product.create(
            payload.name(), 
            payload.category(), 
            payload.description(), 
            payload.price()
        );
        Product saved = productRepository.save(product);
        ProductResponse response = ProductResponse.from(saved);
        return Result.success(response);
    }
}

package br.com.meetingdecoder.application.service;

import br.com.meetingdecoder.application.command.serviceRecommendation.UpdateProductCommand;
import br.com.meetingdecoder.application.dto.ProductResponse;
import br.com.meetingdecoder.application.ports.in.serviceRecommendation.IUpdateProductUseCase;
import br.com.meetingdecoder.domain.insight.repository.IProductRepository;
import br.com.meetingdecoder.application.shared.error.DomainErrorFactory;
import br.com.meetingdecoder.application.shared.result.Result;
import br.com.meetingdecoder.domain.insight.model.Product;

import java.util.Optional;
import java.util.UUID;

public class UpdateProductUseCase implements IUpdateProductUseCase {

    private final IProductRepository productRepository;

    public UpdateProductUseCase(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Result<ProductResponse> execute(UUID id, UpdateProductCommand payload) {
        Optional<Product> foundRecommendation = productRepository.findById(id);
        if (foundRecommendation.isEmpty()) {
            return Result.failure(DomainErrorFactory.notFound("product"));
        }
        Product product = foundRecommendation.get();
        product.update(
                payload.name(),
                payload.category(),
                payload.description(),
                payload.price()
        );
        ProductResponse response = ProductResponse.from(
                productRepository.save(product)
        );
        return Result.success(response);
    }
}

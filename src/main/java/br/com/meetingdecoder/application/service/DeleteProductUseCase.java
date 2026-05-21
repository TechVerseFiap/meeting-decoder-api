package br.com.meetingdecoder.application.service;

import br.com.meetingdecoder.application.ports.in.serviceRecommendation.IDeleteProductUseCase;
import br.com.meetingdecoder.application.ports.out.IProductRepository;
import br.com.meetingdecoder.application.shared.error.DomainErrorFactory;
import br.com.meetingdecoder.application.shared.result.Result;

import java.util.UUID;

public class DeleteProductUseCase implements IDeleteProductUseCase {

    private final IProductRepository productRepository;

    public DeleteProductUseCase(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Result<Void> byId(UUID id) {
        if (!productRepository.existsById(id)) {
            return Result.failure(DomainErrorFactory.notFound("product"));
        }
        productRepository.deleteById(id);
        return Result.success();
    }
}

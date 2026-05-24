package br.com.meetingdecoder.application.service;

import br.com.meetingdecoder.application.dto.QueryOptions;
import br.com.meetingdecoder.application.dto.ProductResponse;
import br.com.meetingdecoder.application.ports.in.serviceRecommendation.IListProductUseCase;
import br.com.meetingdecoder.domain.insight.repository.IProductRepository;
import br.com.meetingdecoder.application.shared.result.Result;
import br.com.meetingdecoder.domain.insight.model.Product;

import java.util.List;

public class ListProductUseCase implements IListProductUseCase {

    private final IProductRepository productRepository;

    public ListProductUseCase(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Result<List<ProductResponse>> all(QueryOptions queryOptions) {
        List<Product> list = productRepository.findAll(queryOptions);
        List<ProductResponse> responseList = ProductResponse.from(list);
        return Result.success(responseList);
    }
}

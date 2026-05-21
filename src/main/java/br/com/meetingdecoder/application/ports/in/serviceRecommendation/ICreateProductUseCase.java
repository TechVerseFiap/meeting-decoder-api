package br.com.meetingdecoder.application.ports.in.serviceRecommendation;

import br.com.meetingdecoder.application.command.serviceRecommendation.CreateProductCommand;
import br.com.meetingdecoder.application.dto.ProductResponse;
import br.com.meetingdecoder.application.shared.result.Result;

public interface ICreateProductUseCase {
    Result<ProductResponse> execute(CreateProductCommand payload);
}

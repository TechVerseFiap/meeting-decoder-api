package br.com.meetingdecoder.application.ports.in.serviceRecommendation;

import br.com.meetingdecoder.application.command.serviceRecommendation.UpdateProductCommand;
import br.com.meetingdecoder.application.dto.ProductResponse;
import br.com.meetingdecoder.application.shared.result.Result;

import java.util.UUID;

public interface IUpdateProductUseCase {
    Result<ProductResponse> execute(UUID id, UpdateProductCommand payload);
}

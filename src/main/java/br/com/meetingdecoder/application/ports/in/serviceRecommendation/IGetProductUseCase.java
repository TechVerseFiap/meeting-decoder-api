package br.com.meetingdecoder.application.ports.in.serviceRecommendation;

import br.com.meetingdecoder.application.dto.ProductResponse;
import br.com.meetingdecoder.application.shared.result.Result;

import java.util.UUID;

public interface IGetProductUseCase {
    Result<ProductResponse> byId(UUID id);
}

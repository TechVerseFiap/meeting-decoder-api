package br.com.meetingdecoder.application.ports.in.serviceRecommendation;

import br.com.meetingdecoder.application.shared.result.Result;

import java.util.UUID;

public interface IDeleteProductUseCase {
    Result<Void> byId(UUID id);
}

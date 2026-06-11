package br.com.meetingdecoder.application.usecase.seller;

import br.com.meetingdecoder.application.shared.result.Result;

import java.util.UUID;

public interface ActivateSellerUseCase {
    Result<Void> execute(UUID sellerId);
}

package br.com.meetingdecoder.application.usecase.seller;

import br.com.meetingdecoder.application.shared.result.Result;

import java.util.UUID;

public interface DeactivateSellerUseCase {
    Result<Void> execute(UUID sellerId);
}

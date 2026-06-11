package br.com.meetingdecoder.application.ports.seller;

import br.com.meetingdecoder.application.shared.result.Result;

import java.util.UUID;

public interface IDeactivateSellerUseCase {
    Result<Void> execute(UUID sellerId);
}

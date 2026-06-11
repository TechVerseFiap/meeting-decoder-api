package br.com.meetingdecoder.application.usecase.seller;

import br.com.meetingdecoder.application.dto.seller.SellerOutput;
import br.com.meetingdecoder.application.shared.result.Result;

import java.util.UUID;

public interface FindSellerByIdUseCase {
    Result<SellerOutput> execute(UUID sellerId);
}

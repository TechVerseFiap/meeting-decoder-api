package br.com.meetingdecoder.application.ports.seller;

import br.com.meetingdecoder.application.dto.seller.SellerOutput;
import br.com.meetingdecoder.application.shared.result.Result;

import java.util.UUID;

public interface IFindSellerByIdUseCase {
    Result<SellerOutput> execute(UUID sellerId);
}

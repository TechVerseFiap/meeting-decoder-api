package br.com.meetingdecoder.application.usecase.seller;

import br.com.meetingdecoder.application.command.seller.UpdateSellerCommand;
import br.com.meetingdecoder.application.dto.seller.SellerOutput;
import br.com.meetingdecoder.application.shared.result.Result;

public interface UpdateSellerUseCase {
    Result<SellerOutput> execute(UpdateSellerCommand command);
}

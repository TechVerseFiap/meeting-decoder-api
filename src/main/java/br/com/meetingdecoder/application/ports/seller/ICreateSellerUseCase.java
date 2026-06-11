package br.com.meetingdecoder.application.ports.seller;

import br.com.meetingdecoder.application.command.seller.CreateSellerCommand;
import br.com.meetingdecoder.application.dto.seller.SellerOutput;
import br.com.meetingdecoder.application.shared.result.Result;

public interface ICreateSellerUseCase {
    Result<SellerOutput> execute(CreateSellerCommand command);
}

package br.com.meetingdecoder.application.ports.seller;

import br.com.meetingdecoder.application.command.seller.UpdateSellerCommand;
import br.com.meetingdecoder.application.dto.seller.SellerOutput;
import br.com.meetingdecoder.application.shared.result.Result;
import br.com.meetingdecoder.domain.sale.valueobject.SellerId;

public interface IUpdateSellerUseCase {
    Result<SellerOutput> execute(SellerId id, UpdateSellerCommand command);
}

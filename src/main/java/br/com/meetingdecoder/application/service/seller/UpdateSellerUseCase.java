package br.com.meetingdecoder.application.service.seller;

import br.com.meetingdecoder.application.command.seller.UpdateSellerCommand;
import br.com.meetingdecoder.application.dto.seller.SellerOutput;
import br.com.meetingdecoder.application.ports.seller.IUpdateSellerUseCase;
import br.com.meetingdecoder.application.shared.result.Result;
import br.com.meetingdecoder.domain.sale.model.Seller;
import br.com.meetingdecoder.domain.sale.repository.ISellerRepository;
import br.com.meetingdecoder.domain.sale.valueobject.SellerId;
import br.com.meetingdecoder.domain.shared.validation.DomainError;
import br.com.meetingdecoder.domain.shared.validation.DomainErrorCode;

public class UpdateSellerUseCase implements IUpdateSellerUseCase {
    private final ISellerRepository repository;

    public UpdateSellerUseCase(ISellerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Result<SellerOutput> execute(SellerId id, UpdateSellerCommand command) {
        Seller seller = repository.findById(id)
                .orElse(null);
        if (seller == null) {
            return Result.failure(
                    DomainError.of(
                            DomainErrorCode.NOT_FOUND,
                            "seller"
                    )
            );
        }
        seller.update(
                command.managerId(),
                command.type(),
                command.name(),
                command.email()
        );
        repository.save(seller);
        return Result.success(SellerOutput.from(seller));
    }
}

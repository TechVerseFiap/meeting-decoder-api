package br.com.meetingdecoder.application.service.seller;

import br.com.meetingdecoder.application.ports.seller.IDeactivateSellerUseCase;
import br.com.meetingdecoder.application.shared.result.Result;
import br.com.meetingdecoder.domain.sale.model.Seller;
import br.com.meetingdecoder.domain.sale.repository.ISellerRepository;
import br.com.meetingdecoder.domain.sale.valueobject.SellerId;
import br.com.meetingdecoder.domain.shared.validation.DomainError;
import br.com.meetingdecoder.domain.shared.validation.DomainErrorCode;

import java.util.UUID;

public class DeactivateSellerUseCase implements IDeactivateSellerUseCase {
    private final ISellerRepository repository;

    public DeactivateSellerUseCase(ISellerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Result<Void> execute(UUID sellerId) {
        Seller seller = repository.findById(SellerId.of(sellerId))
                .orElse(null);
        if (seller == null) {
            return Result.failure(
                    DomainError.of(
                            DomainErrorCode.NOT_FOUND,
                            "sellerId"
                    )
            );
        }
        seller.deactivate();
        repository.save(seller);
        return Result.success();
    }
}
package br.com.meetingdecoder.application.service.seller;

import br.com.meetingdecoder.application.dto.seller.SellerOutput;
import br.com.meetingdecoder.application.ports.seller.IFindSellerByIdUseCase;
import br.com.meetingdecoder.application.shared.result.Result;
import br.com.meetingdecoder.domain.sale.model.Seller;
import br.com.meetingdecoder.domain.sale.repository.ISellerRepository;
import br.com.meetingdecoder.domain.sale.valueobject.SellerId;
import br.com.meetingdecoder.domain.shared.validation.DomainError;
import br.com.meetingdecoder.domain.shared.validation.DomainErrorCode;

import java.util.UUID;

public class FindSellerByIdUseCase implements IFindSellerByIdUseCase {
    private final ISellerRepository repository;

    public FindSellerByIdUseCase(ISellerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Result<SellerOutput> execute(UUID sellerId) {
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
        return Result.success(
                SellerOutput.from(seller)
        );
    }
}
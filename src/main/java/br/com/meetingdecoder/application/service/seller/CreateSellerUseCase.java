package br.com.meetingdecoder.application.service.seller;

import br.com.meetingdecoder.application.command.seller.CreateSellerCommand;
import br.com.meetingdecoder.application.dto.seller.SellerOutput;
import br.com.meetingdecoder.application.ports.seller.ICreateSellerUseCase;
import br.com.meetingdecoder.application.shared.result.Result;
import br.com.meetingdecoder.domain.sale.model.Seller;
import br.com.meetingdecoder.domain.sale.repository.ISellerRepository;
import br.com.meetingdecoder.domain.sale.valueobject.Email;
import br.com.meetingdecoder.domain.sale.valueobject.SellerId;

public class CreateSellerUseCase implements ICreateSellerUseCase {
    private final ISellerRepository repository;

    public CreateSellerUseCase(ISellerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Result<SellerOutput> execute(CreateSellerCommand command) {
        Seller seller = Seller.create(
                SellerId.of(command.id()),
                SellerId.of(command.managerId()),
                command.type(),
                command.name(),
                Email.of(command.email())
        );
        repository.save(seller);
        return Result.success(SellerOutput.from(seller));
    }
}

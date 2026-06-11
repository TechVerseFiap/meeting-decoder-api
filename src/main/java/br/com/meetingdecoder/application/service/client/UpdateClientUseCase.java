package br.com.meetingdecoder.application.service.client;

import br.com.meetingdecoder.application.command.client.UpdateClientCommand;
import br.com.meetingdecoder.application.dto.client.ClientOutput;
import br.com.meetingdecoder.application.ports.client.IUpdateClientUseCase;
import br.com.meetingdecoder.application.shared.result.Result;
import br.com.meetingdecoder.domain.sale.model.Client;
import br.com.meetingdecoder.domain.sale.repository.IClientRepository;
import br.com.meetingdecoder.domain.sale.valueobject.ClientId;
import br.com.meetingdecoder.domain.shared.validation.DomainError;
import br.com.meetingdecoder.domain.shared.validation.DomainErrorCode;

public class UpdateClientUseCase implements IUpdateClientUseCase {
    private final IClientRepository repository;

    public UpdateClientUseCase(IClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public Result<ClientOutput> execute(ClientId id, UpdateClientCommand command) {
        Client client = repository.findById(id)
                .orElse(null);
        if (client == null) {
            return Result.failure(
                    DomainError.of(
                            DomainErrorCode.NOT_FOUND,
                            "client"
                    )
            );
        }
        client.update(
                command.corporateReason(),
                command.fantasyName(),
                command.cnpj(),
                command.cnae(),
                command.segment(),
                command.size(),
                command.city(),
                command.state(),
                command.country(),
                command.type(),
                command.billingRange(),
                command.npsSnapshot()
        );
        repository.save(client);
        return Result.success(ClientOutput.from(client));
    }
}

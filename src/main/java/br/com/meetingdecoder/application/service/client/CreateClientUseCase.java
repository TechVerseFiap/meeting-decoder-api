package br.com.meetingdecoder.application.service.client;

import br.com.meetingdecoder.application.command.client.CreateClientCommand;
import br.com.meetingdecoder.application.dto.client.ClientOutput;
import br.com.meetingdecoder.application.ports.client.ICreateClientUseCase;
import br.com.meetingdecoder.application.shared.result.Result;
import br.com.meetingdecoder.domain.sale.model.Client;
import br.com.meetingdecoder.domain.sale.repository.IClientRepository;
import br.com.meetingdecoder.domain.sale.valueobject.ClientId;

public class CreateClientUseCase implements ICreateClientUseCase {
    private final IClientRepository repository;

    public CreateClientUseCase(IClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public Result<ClientOutput> execute(CreateClientCommand command) {
        Client client = Client.create(
                ClientId.of(command.id()),
                command.externalId(),
                command.corporateReason(),
                command.fantasyName(),
                command.cnpj(),
                command.cnae(),
                command.segment(),
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

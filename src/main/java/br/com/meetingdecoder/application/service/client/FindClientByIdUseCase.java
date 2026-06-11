package br.com.meetingdecoder.application.service.client;

import br.com.meetingdecoder.application.dto.client.ClientOutput;
import br.com.meetingdecoder.application.ports.client.IFindClientByIdUseCase;
import br.com.meetingdecoder.application.shared.result.Result;
import br.com.meetingdecoder.domain.sale.model.Client;
import br.com.meetingdecoder.domain.sale.repository.IClientRepository;
import br.com.meetingdecoder.domain.sale.valueobject.ClientId;
import br.com.meetingdecoder.domain.shared.validation.DomainError;
import br.com.meetingdecoder.domain.shared.validation.DomainErrorCode;

import java.util.UUID;

public class FindClientByIdUseCase implements IFindClientByIdUseCase {
    private final IClientRepository repository;

    public FindClientByIdUseCase(IClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public Result<ClientOutput> execute(UUID clientId) {
        Client client = repository.findById(ClientId.of(clientId))
                .orElse(null);
        if (client == null) {
            return Result.failure(
                    DomainError.of(
                            DomainErrorCode.NOT_FOUND,
                            "clientId"
                    )
            );
        }
        return Result.success(ClientOutput.from(client));
    }
}
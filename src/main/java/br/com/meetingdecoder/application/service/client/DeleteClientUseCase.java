package br.com.meetingdecoder.application.service.client;

import br.com.meetingdecoder.application.ports.client.IDeleteClientUseCase;
import br.com.meetingdecoder.application.shared.result.Result;
import br.com.meetingdecoder.domain.sale.repository.IClientRepository;
import br.com.meetingdecoder.domain.sale.valueobject.ClientId;
import br.com.meetingdecoder.domain.shared.validation.DomainError;
import br.com.meetingdecoder.domain.shared.validation.DomainErrorCode;

import java.util.UUID;

public class DeleteClientUseCase implements IDeleteClientUseCase {
    private final IClientRepository repository;

    public DeleteClientUseCase(IClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public Result<Void> execute(UUID clientId) {
        if (repository.findById(ClientId.of(clientId)).isEmpty()) {
            return Result.failure(
                    DomainError.of(
                            DomainErrorCode.NOT_FOUND,
                            "clientId"
                    )
            );
        }
        repository.deleteById(ClientId.of(clientId));
        return Result.success();
    }
}
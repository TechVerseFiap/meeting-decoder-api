package br.com.meetingdecoder.application.usecase.client;

import br.com.meetingdecoder.application.dto.client.ClientOutput;
import br.com.meetingdecoder.application.shared.result.Result;

import java.util.UUID;

public interface FindClientByIdUseCase {
    Result<ClientOutput> execute(UUID clientId);
}

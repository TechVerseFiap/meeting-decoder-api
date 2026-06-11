package br.com.meetingdecoder.application.ports.client;

import br.com.meetingdecoder.application.dto.client.ClientOutput;
import br.com.meetingdecoder.application.shared.result.Result;

import java.util.UUID;

public interface IFindClientByIdUseCase {
    Result<ClientOutput> execute(UUID clientId);
}

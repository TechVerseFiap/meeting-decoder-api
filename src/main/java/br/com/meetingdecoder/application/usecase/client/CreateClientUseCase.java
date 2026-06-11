package br.com.meetingdecoder.application.usecase.client;

import br.com.meetingdecoder.application.command.client.CreateClientCommand;
import br.com.meetingdecoder.application.dto.client.ClientOutput;
import br.com.meetingdecoder.application.shared.result.Result;

public interface CreateClientUseCase {
    Result<ClientOutput> execute(CreateClientCommand command);
}

package br.com.meetingdecoder.application.ports.client;

import br.com.meetingdecoder.application.command.client.CreateClientCommand;
import br.com.meetingdecoder.application.dto.client.ClientOutput;
import br.com.meetingdecoder.application.shared.result.Result;

public interface ICreateClientUseCase {
    Result<ClientOutput> execute(CreateClientCommand command);
}

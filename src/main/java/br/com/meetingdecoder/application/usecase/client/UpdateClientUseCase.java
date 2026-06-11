package br.com.meetingdecoder.application.usecase.client;

import br.com.meetingdecoder.application.command.client.UpdateClientCommand;
import br.com.meetingdecoder.application.dto.client.ClientOutput;
import br.com.meetingdecoder.application.shared.result.Result;

public interface UpdateClientUseCase {
    Result<ClientOutput> execute(UpdateClientCommand command);
}

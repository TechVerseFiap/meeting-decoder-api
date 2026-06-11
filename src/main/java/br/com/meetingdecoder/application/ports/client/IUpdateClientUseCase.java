package br.com.meetingdecoder.application.ports.client;

import br.com.meetingdecoder.application.command.client.UpdateClientCommand;
import br.com.meetingdecoder.application.dto.client.ClientOutput;
import br.com.meetingdecoder.application.shared.result.Result;
import br.com.meetingdecoder.domain.sale.valueobject.ClientId;

public interface IUpdateClientUseCase {
    Result<ClientOutput> execute(ClientId id, UpdateClientCommand command);
}

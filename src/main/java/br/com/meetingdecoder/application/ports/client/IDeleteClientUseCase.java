package br.com.meetingdecoder.application.ports.client;

import br.com.meetingdecoder.application.shared.result.Result;

import java.util.UUID;

public interface IDeleteClientUseCase {
    Result<Void> execute(UUID clientId);
}

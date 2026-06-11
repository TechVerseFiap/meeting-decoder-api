package br.com.meetingdecoder.application.usecase.client;

import br.com.meetingdecoder.application.shared.result.Result;

import java.util.UUID;

public interface DeleteClientUseCase {
    Result<Void> execute(UUID clientId);
}

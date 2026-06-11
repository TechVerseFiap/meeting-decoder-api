package br.com.meetingdecoder.application.usecase.transcription;

import br.com.meetingdecoder.application.command.transcription.CreateTranscriptionCommand;
import br.com.meetingdecoder.application.dto.transcription.TranscriptionOutput;
import br.com.meetingdecoder.application.shared.result.Result;

public interface CreateTranscriptionUseCase {
    Result<TranscriptionOutput> execute(CreateTranscriptionCommand command);
}

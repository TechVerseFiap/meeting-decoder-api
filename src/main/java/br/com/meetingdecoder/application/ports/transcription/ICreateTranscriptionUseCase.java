package br.com.meetingdecoder.application.ports.transcription;

import br.com.meetingdecoder.application.command.transcription.CreateTranscriptionCommand;
import br.com.meetingdecoder.application.dto.transcription.TranscriptionOutput;
import br.com.meetingdecoder.application.shared.result.Result;

public interface ICreateTranscriptionUseCase {
    Result<TranscriptionOutput> execute(CreateTranscriptionCommand command);
}

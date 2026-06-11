package br.com.meetingdecoder.application.usecase.transcription;

import br.com.meetingdecoder.application.command.transcription.UpdateTranscriptionCommand;
import br.com.meetingdecoder.application.dto.transcription.TranscriptionOutput;
import br.com.meetingdecoder.application.shared.result.Result;

public interface UpdateTranscriptionUseCase {
    Result<TranscriptionOutput> execute(UpdateTranscriptionCommand command);
}

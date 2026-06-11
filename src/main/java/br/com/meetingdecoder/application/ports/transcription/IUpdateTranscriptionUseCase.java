package br.com.meetingdecoder.application.ports.transcription;

import br.com.meetingdecoder.application.command.transcription.UpdateTranscriptionCommand;
import br.com.meetingdecoder.application.dto.transcription.TranscriptionOutput;
import br.com.meetingdecoder.application.shared.result.Result;
import br.com.meetingdecoder.domain.transcription.valueobject.TranscriptionId;

public interface IUpdateTranscriptionUseCase {
    Result<TranscriptionOutput> execute(TranscriptionId id, UpdateTranscriptionCommand command);
}

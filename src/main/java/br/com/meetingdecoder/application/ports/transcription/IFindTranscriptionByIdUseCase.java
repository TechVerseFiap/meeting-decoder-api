package br.com.meetingdecoder.application.ports.transcription;

import br.com.meetingdecoder.application.dto.transcription.TranscriptionOutput;
import br.com.meetingdecoder.application.shared.result.Result;

import java.util.UUID;

public interface IFindTranscriptionByIdUseCase {
    Result<TranscriptionOutput> execute(UUID transcriptionId);
}

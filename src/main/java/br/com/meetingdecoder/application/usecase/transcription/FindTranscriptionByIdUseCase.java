package br.com.meetingdecoder.application.usecase.transcription;

import br.com.meetingdecoder.application.dto.transcription.TranscriptionOutput;
import br.com.meetingdecoder.application.shared.result.Result;

import java.util.UUID;

public interface FindTranscriptionByIdUseCase {
    Result<TranscriptionOutput> execute(UUID transcriptionId);
}

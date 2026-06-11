package br.com.meetingdecoder.application.service.transcription;

import br.com.meetingdecoder.application.dto.transcription.TranscriptionOutput;
import br.com.meetingdecoder.application.ports.transcription.IFindTranscriptionByIdUseCase;
import br.com.meetingdecoder.application.shared.result.Result;
import br.com.meetingdecoder.domain.shared.validation.DomainError;
import br.com.meetingdecoder.domain.shared.validation.DomainErrorCode;
import br.com.meetingdecoder.domain.transcription.model.Transcription;
import br.com.meetingdecoder.domain.transcription.repository.ITranscriptionRepository;
import br.com.meetingdecoder.domain.transcription.valueobject.TranscriptionId;

import java.util.UUID;

public class FindTranscriptionByIdUseCase implements IFindTranscriptionByIdUseCase {
    private final ITranscriptionRepository repository;

    public FindTranscriptionByIdUseCase(ITranscriptionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Result<TranscriptionOutput> execute(UUID transcriptionId) {
        Transcription transcription = repository.findById(TranscriptionId.of(transcriptionId))
                .orElse(null);
        if (transcription == null) {
            return Result.failure(
                    DomainError.of(
                            DomainErrorCode.NOT_FOUND,
                            "transcriptionId"
                    )
            );
        }
        return Result.success(
                TranscriptionOutput.from(transcription)
        );
    }
}
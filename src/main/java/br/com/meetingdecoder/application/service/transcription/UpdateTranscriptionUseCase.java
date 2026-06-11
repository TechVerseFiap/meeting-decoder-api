package br.com.meetingdecoder.application.service.transcription;

import br.com.meetingdecoder.application.command.transcription.UpdateTranscriptionCommand;
import br.com.meetingdecoder.application.dto.transcription.TranscriptionOutput;
import br.com.meetingdecoder.application.ports.transcription.IUpdateTranscriptionUseCase;
import br.com.meetingdecoder.application.shared.result.Result;
import br.com.meetingdecoder.domain.shared.validation.DomainError;
import br.com.meetingdecoder.domain.shared.validation.DomainErrorCode;
import br.com.meetingdecoder.domain.transcription.model.Transcription;
import br.com.meetingdecoder.domain.transcription.repository.ITranscriptionRepository;
import br.com.meetingdecoder.domain.transcription.valueobject.TranscriptionId;

public class UpdateTranscriptionUseCase implements IUpdateTranscriptionUseCase {
    private final ITranscriptionRepository repository;

    public UpdateTranscriptionUseCase(ITranscriptionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Result<TranscriptionOutput> execute(TranscriptionId id, UpdateTranscriptionCommand command) {
        Transcription transcription = repository
                .findById(id)
                .orElse(null);
        if (transcription == null) {
            return Result.failure(
                    DomainError.of(
                            DomainErrorCode.NOT_FOUND,
                            "transcription"
                    )
            );
        }
        transcription.update(
                command.rawText(),
                command.cleanText(),
                command.formattedText(),
                command.modelConfidence(),
                command.processedAt(),
                command.finishedAt()
        );
        repository.save(transcription);
        return Result.success(
                TranscriptionOutput.from(transcription)
        );
    }
}

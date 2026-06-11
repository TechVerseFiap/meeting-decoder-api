package br.com.meetingdecoder.application.service.transcription;

import br.com.meetingdecoder.application.command.transcription.CreateTranscriptionCommand;
import br.com.meetingdecoder.application.dto.transcription.TranscriptionOutput;
import br.com.meetingdecoder.application.ports.transcription.ICreateTranscriptionUseCase;
import br.com.meetingdecoder.application.shared.result.Result;
import br.com.meetingdecoder.domain.transcription.model.Transcription;
import br.com.meetingdecoder.domain.transcription.repository.ITranscriptionRepository;
import br.com.meetingdecoder.domain.transcription.valueobject.MeetingId;
import br.com.meetingdecoder.domain.transcription.valueobject.TranscriptJson;
import br.com.meetingdecoder.domain.transcription.valueobject.TranscriptionConfidence;
import br.com.meetingdecoder.domain.transcription.valueobject.TranscriptionId;

public class CreateTranscriptionUseCase implements ICreateTranscriptionUseCase {
    private final ITranscriptionRepository repository;

    public CreateTranscriptionUseCase(ITranscriptionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Result<TranscriptionOutput> execute(CreateTranscriptionCommand command) {
        Transcription transcription = Transcription.create(
                TranscriptionId.of(command.id()),
                MeetingId.of(command.meetingId()),
                command.rawText(),
                command.cleanText(),
                TranscriptJson.of(command.formattedText()),
                TranscriptionConfidence.of(command.confidence()),
                command.processedAt(),
                command.finishedAt()
        );
        repository.save(transcription);
        return Result.success(
                TranscriptionOutput.from(transcription)
        );
    }
}

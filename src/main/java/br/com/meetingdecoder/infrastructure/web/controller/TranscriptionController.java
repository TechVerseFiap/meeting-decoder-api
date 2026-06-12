package br.com.meetingdecoder.infrastructure.web.controller;

import br.com.meetingdecoder.application.command.transcription.CreateTranscriptionCommand;
import br.com.meetingdecoder.application.command.transcription.UpdateTranscriptionCommand;
import br.com.meetingdecoder.application.dto.transcription.TranscriptionOutput;
import br.com.meetingdecoder.application.ports.transcription.*;
import br.com.meetingdecoder.application.shared.result.Result;
import br.com.meetingdecoder.domain.transcription.valueobject.TranscriptJson;
import br.com.meetingdecoder.domain.transcription.valueobject.TranscriptionConfidence;
import br.com.meetingdecoder.domain.transcription.valueobject.TranscriptionId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/transcriptions")
@RequiredArgsConstructor
public class TranscriptionController {

    private final ICreateTranscriptionUseCase createTranscriptionUseCase;
    private final IUpdateTranscriptionUseCase updateTranscriptionUseCase;
    private final IFindTranscriptionByIdUseCase findTranscriptionByIdUseCase;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateTranscriptionRequest request) {
        CreateTranscriptionCommand command = new CreateTranscriptionCommand(
                UUID.randomUUID(),
                request.meetingId(),
                request.rawText(),
                request.cleanText(),
                request.formattedText(),
                request.confidence(),
                request.processedAt(),
                request.finishedAt()
        );

        Result<TranscriptionOutput> result = createTranscriptionUseCase.execute(command);

        if (result.isFailure()) {
            return ResponseEntity.unprocessableEntity().body(result.getErrors());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result.getData());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        Result<TranscriptionOutput> result = findTranscriptionByIdUseCase.execute(id);

        if (result.isFailure()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result.getErrors());
        }

        return ResponseEntity.ok(result.getData());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody UpdateTranscriptionRequest request) {
        TranscriptJson formattedText = request.formattedText() != null
                ? TranscriptJson.of(request.formattedText())
                : null;

        TranscriptionConfidence confidence = request.confidence() != null
                ? TranscriptionConfidence.of(request.confidence())
                : null;

        UpdateTranscriptionCommand command = UpdateTranscriptionCommand.builder()
                .rawText(request.rawText())
                .cleanText(request.cleanText())
                .formattedText(formattedText)
                .modelConfidence(confidence)
                .processedAt(request.processedAt())
                .finishedAt(request.finishedAt())
                .build();

        Result<TranscriptionOutput> result = updateTranscriptionUseCase.execute(
                TranscriptionId.of(id), command
        );

        if (result.isFailure()) {
            return ResponseEntity.unprocessableEntity().body(result.getErrors());
        }

        return ResponseEntity.ok(result.getData());
    }

    public record CreateTranscriptionRequest(
            UUID meetingId,
            String rawText,
            String cleanText,
            String formattedText,
            Double confidence,
            LocalDateTime processedAt,
            LocalDateTime finishedAt
    ) {}

    public record UpdateTranscriptionRequest(
            String rawText,
            String cleanText,
            String formattedText,
            Double confidence,
            LocalDateTime processedAt,
            LocalDateTime finishedAt
    ) {}
}

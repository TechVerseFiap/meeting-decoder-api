package br.com.meetingdecoder.infrastructure.web.controller;

import br.com.meetingdecoder.application.command.meeting.CreateMeetingCommand;
import br.com.meetingdecoder.application.command.meeting.UpdateMeetingCommand;
import br.com.meetingdecoder.application.dto.meeting.MeetingOutput;
import br.com.meetingdecoder.application.ports.meeting.*;
import br.com.meetingdecoder.application.shared.result.Result;
import br.com.meetingdecoder.domain.sale.valueobject.ClientId;
import br.com.meetingdecoder.domain.sale.valueobject.SellerId;
import br.com.meetingdecoder.domain.transcription.enums.MeetingStatus;
import br.com.meetingdecoder.domain.transcription.valueobject.MeetingId;
import br.com.meetingdecoder.domain.transcription.valueobject.MeetingPeriod;
import br.com.meetingdecoder.domain.transcription.valueobject.Participants;
import br.com.meetingdecoder.domain.transcription.valueobject.RecordingUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/meetings")
@RequiredArgsConstructor
public class MeetingController {

    private final ICreateMeetingUseCase createMeetingUseCase;
    private final IUpdateMeetingUseCase updateMeetingUseCase;
    private final IFindMeetingByIdUseCase findMeetingByIdUseCase;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateMeetingRequest request) {
        CreateMeetingCommand command = new CreateMeetingCommand(
                UUID.randomUUID(),
                request.externalId(),
                request.meetingDate(),
                request.startTime(),
                request.endTime(),
                request.status(),
                request.external(),
                request.recordingUrl(),
                request.sellerId(),
                request.clientId()
        );

        Result<MeetingOutput> result = createMeetingUseCase.execute(command);

        if (result.isFailure()) {
            return ResponseEntity.unprocessableContent().body(result.getErrors());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result.getData());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        Result<MeetingOutput> result = findMeetingByIdUseCase.execute(id);

        if (result.isFailure()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result.getErrors());
        }

        return ResponseEntity.ok(result.getData());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody UpdateMeetingRequest request) {
        MeetingPeriod period = (request.startTime() != null && request.endTime() != null)
                ? MeetingPeriod.of(request.startTime(), request.endTime())
                : null;

        Participants participants = (request.sellerId() != null && request.clientId() != null)
                ? Participants.of(SellerId.of(request.sellerId()), ClientId.of(request.clientId()))
                : null;

        RecordingUrl recordingUrl = request.recordingUrl() != null
                ? RecordingUrl.of(request.recordingUrl())
                : null;

        UpdateMeetingCommand command = UpdateMeetingCommand.builder()
                .externalId(request.externalId())
                .meetingDate(request.meetingDate())
                .meetingPeriod(period)
                .status(request.status())
                .external(request.external())
                .recordingUrl(recordingUrl)
                .participants(participants)
                .build();

        Result<MeetingOutput> result = updateMeetingUseCase.execute(MeetingId.of(id), command);

        if (result.isFailure()) {
            return ResponseEntity.unprocessableContent().body(result.getErrors());
        }

        return ResponseEntity.ok(result.getData());
    }

    public record CreateMeetingRequest(
            String externalId,
            LocalDateTime meetingDate,
            LocalDateTime startTime,
            LocalDateTime endTime,
            MeetingStatus status,
            Boolean external,
            String recordingUrl,
            UUID sellerId,
            UUID clientId
    ) {}

    public record UpdateMeetingRequest(
            String externalId,
            LocalDateTime meetingDate,
            LocalDateTime startTime,
            LocalDateTime endTime,
            MeetingStatus status,
            Boolean external,
            String recordingUrl,
            UUID sellerId,
            UUID clientId
    ) {}
}

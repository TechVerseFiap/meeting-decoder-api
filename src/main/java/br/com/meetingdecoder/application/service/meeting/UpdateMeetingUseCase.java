package br.com.meetingdecoder.application.service.meeting;

import br.com.meetingdecoder.application.command.meeting.UpdateMeetingCommand;
import br.com.meetingdecoder.application.dto.meeting.MeetingOutput;
import br.com.meetingdecoder.application.ports.meeting.IUpdateMeetingUseCase;
import br.com.meetingdecoder.application.shared.result.Result;
import br.com.meetingdecoder.domain.shared.validation.DomainError;
import br.com.meetingdecoder.domain.shared.validation.DomainErrorCode;
import br.com.meetingdecoder.domain.transcription.model.Meeting;
import br.com.meetingdecoder.domain.transcription.repository.IMeetingRepository;
import br.com.meetingdecoder.domain.transcription.valueobject.MeetingId;

public class UpdateMeetingUseCase implements IUpdateMeetingUseCase {
    private final IMeetingRepository repository;

    public UpdateMeetingUseCase(IMeetingRepository repository) {
        this.repository = repository;
    }

    @Override
    public Result<MeetingOutput> execute(MeetingId id, UpdateMeetingCommand command) {
        Meeting meeting = repository.findById(id)
                .orElse(null);
        if (meeting == null) {
            return Result.failure(
                    DomainError.of(
                            DomainErrorCode.NOT_FOUND,
                            "meeting"
                    )
            );
        }
        meeting.update(
                command.externalId(),
                command.meetingDate(),
                command.meetingPeriod(),
                command.status(),
                command.external(),
                command.recordingUrl(),
                command.participants()
        );
        repository.save(meeting);
        return Result.success(MeetingOutput.from(meeting));
    }
}

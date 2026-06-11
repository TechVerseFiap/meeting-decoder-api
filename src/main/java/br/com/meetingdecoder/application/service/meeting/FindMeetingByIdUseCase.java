package br.com.meetingdecoder.application.service.meeting;

import br.com.meetingdecoder.application.dto.meeting.MeetingOutput;
import br.com.meetingdecoder.application.ports.meeting.IFindMeetingByIdUseCase;
import br.com.meetingdecoder.application.shared.result.Result;
import br.com.meetingdecoder.domain.shared.validation.DomainError;
import br.com.meetingdecoder.domain.shared.validation.DomainErrorCode;
import br.com.meetingdecoder.domain.transcription.model.Meeting;
import br.com.meetingdecoder.domain.transcription.repository.IMeetingRepository;
import br.com.meetingdecoder.domain.transcription.valueobject.MeetingId;

import java.util.UUID;

public class FindMeetingByIdUseCase implements IFindMeetingByIdUseCase {
    private final IMeetingRepository repository;

    public FindMeetingByIdUseCase(IMeetingRepository repository) {
        this.repository = repository;
    }

    @Override
    public Result<MeetingOutput> execute(UUID meetingId) {
        Meeting meeting = repository.findById(MeetingId.of(meetingId))
                .orElse(null);
        if (meeting == null) {
            return Result.failure(
                    DomainError.of(
                            DomainErrorCode.NOT_FOUND,
                            "meetingId"
                    )
            );
        }
        return Result.success(
                MeetingOutput.from(meeting)
        );
    }
}
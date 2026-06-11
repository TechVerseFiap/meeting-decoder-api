package br.com.meetingdecoder.application.usecase.meeting;

import br.com.meetingdecoder.application.dto.meeting.MeetingOutput;
import br.com.meetingdecoder.application.shared.result.Result;

import java.util.UUID;

public interface FindMeetingByIdUseCase {
    Result<MeetingOutput> execute(UUID meetingId);
}

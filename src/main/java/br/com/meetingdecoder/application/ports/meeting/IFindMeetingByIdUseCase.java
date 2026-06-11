package br.com.meetingdecoder.application.ports.meeting;

import br.com.meetingdecoder.application.dto.meeting.MeetingOutput;
import br.com.meetingdecoder.application.shared.result.Result;

import java.util.UUID;

public interface IFindMeetingByIdUseCase {
    Result<MeetingOutput> execute(UUID meetingId);
}

package br.com.meetingdecoder.application.ports.meeting;

import br.com.meetingdecoder.application.command.meeting.UpdateMeetingCommand;
import br.com.meetingdecoder.application.dto.meeting.MeetingOutput;
import br.com.meetingdecoder.application.shared.result.Result;
import br.com.meetingdecoder.domain.transcription.valueobject.MeetingId;

import java.util.UUID;

public interface IUpdateMeetingUseCase {
    Result<MeetingOutput> execute(MeetingId id, UpdateMeetingCommand command);
}

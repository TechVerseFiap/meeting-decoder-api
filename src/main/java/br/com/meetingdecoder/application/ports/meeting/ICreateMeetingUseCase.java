package br.com.meetingdecoder.application.ports.meeting;

import br.com.meetingdecoder.application.command.meeting.CreateMeetingCommand;
import br.com.meetingdecoder.application.dto.meeting.MeetingOutput;
import br.com.meetingdecoder.application.shared.result.Result;

public interface ICreateMeetingUseCase {
    Result<MeetingOutput> execute(CreateMeetingCommand command);
}

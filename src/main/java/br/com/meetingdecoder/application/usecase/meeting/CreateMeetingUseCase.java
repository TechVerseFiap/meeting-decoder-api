package br.com.meetingdecoder.application.usecase.meeting;

import br.com.meetingdecoder.application.command.meeting.CreateMeetingCommand;
import br.com.meetingdecoder.application.dto.meeting.MeetingOutput;
import br.com.meetingdecoder.application.shared.result.Result;

public interface CreateMeetingUseCase {
    Result<MeetingOutput> execute(CreateMeetingCommand command);
}

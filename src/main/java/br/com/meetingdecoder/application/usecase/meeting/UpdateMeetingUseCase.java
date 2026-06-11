package br.com.meetingdecoder.application.usecase.meeting;

import br.com.meetingdecoder.application.command.meeting.UpdateMeetingCommand;
import br.com.meetingdecoder.application.dto.meeting.MeetingOutput;
import br.com.meetingdecoder.application.shared.result.Result;

public interface UpdateMeetingUseCase {
    Result<MeetingOutput> execute(UpdateMeetingCommand command);
}

package br.com.meetingdecoder.application.service.meeting;

import br.com.meetingdecoder.application.command.meeting.CreateMeetingCommand;
import br.com.meetingdecoder.application.dto.meeting.MeetingOutput;
import br.com.meetingdecoder.application.ports.meeting.ICreateMeetingUseCase;
import br.com.meetingdecoder.application.shared.result.Result;
import br.com.meetingdecoder.domain.sale.valueobject.ClientId;
import br.com.meetingdecoder.domain.sale.valueobject.SellerId;
import br.com.meetingdecoder.domain.transcription.model.Meeting;
import br.com.meetingdecoder.domain.transcription.repository.IMeetingRepository;
import br.com.meetingdecoder.domain.transcription.valueobject.MeetingId;
import br.com.meetingdecoder.domain.transcription.valueobject.MeetingPeriod;
import br.com.meetingdecoder.domain.transcription.valueobject.Participants;
import br.com.meetingdecoder.domain.transcription.valueobject.RecordingUrl;

public class CreateMeetingUseCase implements ICreateMeetingUseCase {
    private final IMeetingRepository repository;

    public CreateMeetingUseCase(IMeetingRepository repository) {
        this.repository = repository;
    }

    @Override
    public Result<MeetingOutput> execute(CreateMeetingCommand command) {
        Meeting meeting = Meeting.create(
                MeetingId.of(command.id()),
                command.externalId(),
                command.meetingDate(),
                MeetingPeriod.of(command.startTime(), command.endTime()),
                command.status(),
                command.external(),
                RecordingUrl.of(command.recordingUrl()),
                Participants.of(SellerId.of(command.sellerId()), ClientId.of(command.clientId()))
        );
        repository.save(meeting);
        return Result.success(MeetingOutput.from(meeting));
    }
}

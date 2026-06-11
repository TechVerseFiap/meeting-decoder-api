package br.com.meetingdecoder.application.command.meeting;

import br.com.meetingdecoder.domain.transcription.enums.MeetingStatus;
import br.com.meetingdecoder.domain.transcription.valueobject.MeetingPeriod;
import br.com.meetingdecoder.domain.transcription.valueobject.Participants;
import br.com.meetingdecoder.domain.transcription.valueobject.RecordingUrl;

import java.time.LocalDateTime;

public record UpdateMeetingCommand(
        String externalId,
        LocalDateTime meetingDate,
        MeetingPeriod meetingPeriod,
        MeetingStatus status,
        Boolean external,
        RecordingUrl recordingUrl,
        Participants participants
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String externalId;
        private LocalDateTime meetingDate;
        private MeetingPeriod meetingPeriod;
        private MeetingStatus status;
        private Boolean external;
        private RecordingUrl recordingUrl;
        private Participants participants;

        public Builder externalId(String value) {
            this.externalId = value;
            return this;
        }

        public Builder meetingDate(LocalDateTime value) {
            this.meetingDate = value;
            return this;
        }

        public Builder meetingPeriod(MeetingPeriod value) {
            this.meetingPeriod = value;
            return this;
        }

        public Builder status(MeetingStatus value) {
            this.status = value;
            return this;
        }

        public Builder external(Boolean value) {
            this.external = value;
            return this;
        }

        public Builder recordingUrl(RecordingUrl value) {
            this.recordingUrl = value;
            return this;
        }

        public Builder participants(Participants value) {
            this.participants = value;
            return this;
        }

        public UpdateMeetingCommand build() {
            return new UpdateMeetingCommand(
                    externalId,
                    meetingDate,
                    meetingPeriod,
                    status,
                    external,
                    recordingUrl,
                    participants
            );
        }
    }
}

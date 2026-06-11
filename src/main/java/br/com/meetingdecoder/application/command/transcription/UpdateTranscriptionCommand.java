package br.com.meetingdecoder.application.command.transcription;

import br.com.meetingdecoder.domain.transcription.valueobject.TranscriptJson;
import br.com.meetingdecoder.domain.transcription.valueobject.TranscriptionConfidence;

import java.time.LocalDateTime;

public record UpdateTranscriptionCommand(
        String rawText,
        String cleanText,
        TranscriptJson formattedText,
        TranscriptionConfidence modelConfidence,
        LocalDateTime processedAt,
        LocalDateTime finishedAt
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String rawText;
        private String cleanText;
        private TranscriptJson formattedText;
        private TranscriptionConfidence modelConfidence;
        private LocalDateTime processedAt;
        private LocalDateTime finishedAt;

        public Builder rawText(String value) {
            this.rawText = value;
            return this;
        }

        public Builder cleanText(String value) {
            this.cleanText = value;
            return this;
        }

        public Builder formattedText(TranscriptJson value) {
            this.formattedText = value;
            return this;
        }

        public Builder modelConfidence(TranscriptionConfidence value) {
            this.modelConfidence = value;
            return this;
        }

        public Builder processedAt(LocalDateTime value) {
            this.processedAt = value;
            return this;
        }

        public Builder finishedAt(LocalDateTime value) {
            this.finishedAt = value;
            return this;
        }

        public UpdateTranscriptionCommand build() {
            return new UpdateTranscriptionCommand(
                    rawText,
                    cleanText,
                    formattedText,
                    modelConfidence,
                    processedAt,
                    finishedAt
            );
        }
    }
}

package br.com.meetingdecoder.domain.transcription;

import br.com.meetingdecoder.domain.shared.exception.DomainValidationException;
import br.com.meetingdecoder.domain.transcription.enums.MeetingStatus;
import br.com.meetingdecoder.domain.transcription.enums.RankingTranscriptionConfidence;
import br.com.meetingdecoder.domain.transcription.model.Meeting;
import br.com.meetingdecoder.domain.transcription.model.Transcription;
import br.com.meetingdecoder.domain.transcription.valueobject.MeetingPeriod;
import br.com.meetingdecoder.domain.transcription.valueobject.TranscriptJson;
import br.com.meetingdecoder.domain.transcription.valueobject.TranscriptionConfidence;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static br.com.meetingdecoder.support.TestFixtures.meeting;
import static br.com.meetingdecoder.support.TestFixtures.transcription;
import static org.junit.jupiter.api.Assertions.*;

class TranscriptionDomainTest {

    @Test
    void shouldCalculateMeetingDurationAndRejectInvalidPeriods() {
        LocalDateTime end = LocalDateTime.now().minusMinutes(1);
        MeetingPeriod period = MeetingPeriod.of(end.minusMinutes(90), end);

        assertEquals(90, period.durationInMinutes());
        assertEquals(5_400, period.durationInSeconds());

        DomainValidationException future = assertThrows(
                DomainValidationException.class,
                () -> MeetingPeriod.of(LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2))
        );
        assertEquals(2, future.getErrors().size());

        assertThrows(
                DomainValidationException.class,
                () -> MeetingPeriod.of(end, end.minusMinutes(1))
        );
    }

    @Test
    void shouldClassifyTranscriptionConfidenceAtBoundaries() {
        assertEquals(RankingTranscriptionConfidence.LOW, TranscriptionConfidence.of(0.4).rankingConfidence());
        assertEquals(RankingTranscriptionConfidence.MEDIUM, TranscriptionConfidence.of(0.41).rankingConfidence());
        assertEquals(RankingTranscriptionConfidence.HIGH, TranscriptionConfidence.of(0.7).rankingConfidence());

        assertThrows(DomainValidationException.class, () -> TranscriptionConfidence.of(-0.01));
        assertThrows(DomainValidationException.class, () -> TranscriptionConfidence.of(1.01));
    }

    @Test
    void shouldRequireTranscriptJsonObject() {
        assertEquals("{\"ok\":true}", TranscriptJson.of("  {\"ok\":true}").value().trim());
        assertThrows(DomainValidationException.class, () -> TranscriptJson.of("[]"));
        assertThrows(DomainValidationException.class, () -> TranscriptJson.of(" "));
    }

    @Test
    void shouldCreateAndPartiallyUpdateMeeting() {
        Meeting meeting = meeting();
        long previousDuration = meeting.durationInSeconds();

        LocalDateTime end = LocalDateTime.now().minusMinutes(1);
        meeting.update(
                "updated-external-id", null,
                MeetingPeriod.of(end.minusMinutes(30), end),
                MeetingStatus.CANCELLED, true, null, null
        );

        assertEquals("updated-external-id", meeting.externalId());
        assertEquals(MeetingStatus.CANCELLED, meeting.status());
        assertTrue(meeting.external());
        assertEquals(1_800, meeting.durationInSeconds());
        assertNotEquals(previousDuration, meeting.durationInSeconds());
        assertNotNull(meeting.updatedAt());
    }

    @Test
    void shouldRejectTranscriptionFinishedBeforeProcessing() {
        Transcription transcription = transcription();

        DomainValidationException exception = assertThrows(
                DomainValidationException.class,
                () -> transcription.update(
                        null, null, null, null,
                        LocalDateTime.now(), LocalDateTime.now().minusHours(1)
                )
        );

        assertTrue(exception.hasField("finishedAt"));
    }

    @Test
    void shouldPartiallyUpdateTranscription() {
        Transcription transcription = transcription();
        transcription.update(
                "new raw", "new clean", TranscriptJson.of("{\"new\":true}"),
                TranscriptionConfidence.of(0.5), null, null
        );

        assertEquals("new raw", transcription.rawText());
        assertEquals("new clean", transcription.cleanText());
        assertEquals(0.5, transcription.modelConfidence().value());
        assertNotNull(transcription.updatedAt());
    }
}

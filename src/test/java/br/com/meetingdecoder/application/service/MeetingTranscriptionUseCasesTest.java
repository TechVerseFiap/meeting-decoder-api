package br.com.meetingdecoder.application.service;

import br.com.meetingdecoder.application.command.meeting.CreateMeetingCommand;
import br.com.meetingdecoder.application.command.meeting.UpdateMeetingCommand;
import br.com.meetingdecoder.application.command.transcription.CreateTranscriptionCommand;
import br.com.meetingdecoder.application.command.transcription.UpdateTranscriptionCommand;
import br.com.meetingdecoder.application.service.meeting.CreateMeetingUseCase;
import br.com.meetingdecoder.application.service.meeting.FindMeetingByIdUseCase;
import br.com.meetingdecoder.application.service.meeting.UpdateMeetingUseCase;
import br.com.meetingdecoder.application.service.transcription.CreateTranscriptionUseCase;
import br.com.meetingdecoder.application.service.transcription.FindTranscriptionByIdUseCase;
import br.com.meetingdecoder.application.service.transcription.UpdateTranscriptionUseCase;
import br.com.meetingdecoder.domain.transcription.enums.MeetingStatus;
import br.com.meetingdecoder.domain.transcription.model.Meeting;
import br.com.meetingdecoder.domain.transcription.model.Transcription;
import br.com.meetingdecoder.domain.transcription.repository.IMeetingRepository;
import br.com.meetingdecoder.domain.transcription.repository.ITranscriptionRepository;
import br.com.meetingdecoder.domain.transcription.valueobject.MeetingId;
import br.com.meetingdecoder.domain.transcription.valueobject.TranscriptJson;
import br.com.meetingdecoder.domain.transcription.valueobject.TranscriptionConfidence;
import br.com.meetingdecoder.domain.transcription.valueobject.TranscriptionId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static br.com.meetingdecoder.support.TestFixtures.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MeetingTranscriptionUseCasesTest {
    private IMeetingRepository meetingRepository;
    private ITranscriptionRepository transcriptionRepository;

    @BeforeEach
    void setUp() {
        meetingRepository = mock(IMeetingRepository.class);
        transcriptionRepository = mock(ITranscriptionRepository.class);
    }

    @Test
    void shouldCreateMeetingAndPersistAggregate() {
        CreateMeetingCommand command = new CreateMeetingCommand(
                MEETING_ID, "external", NOW, NOW.minusHours(1), NOW,
                MeetingStatus.COMPLETED, false, "https://example.com/audio",
                SELLER_ID, CLIENT_ID
        );

        var result = new CreateMeetingUseCase(meetingRepository).execute(command);

        assertEquals(3_600, result.getData().durationInSeconds());
        verify(meetingRepository).save(any(Meeting.class));
    }

    @Test
    void shouldFindMeetingOrReturnNotFound() {
        when(meetingRepository.findById(any())).thenReturn(Optional.of(meeting()), Optional.empty());
        FindMeetingByIdUseCase useCase = new FindMeetingByIdUseCase(meetingRepository);

        assertEquals(MEETING_ID, useCase.execute(MEETING_ID).getData().id());
        assertTrue(useCase.execute(MEETING_ID).isFailure());
    }

    @Test
    void shouldUpdateMeetingOrReturnNotFound() {
        Meeting meeting = meeting();
        when(meetingRepository.findById(any())).thenReturn(Optional.of(meeting), Optional.empty());
        UpdateMeetingUseCase useCase = new UpdateMeetingUseCase(meetingRepository);

        var result = useCase.execute(
                MeetingId.of(MEETING_ID),
                UpdateMeetingCommand.builder().status(MeetingStatus.CANCELLED).build()
        );
        assertEquals(MeetingStatus.CANCELLED, result.getData().status());
        verify(meetingRepository).save(meeting);

        assertTrue(useCase.execute(
                MeetingId.of(MEETING_ID), UpdateMeetingCommand.builder().build()
        ).isFailure());
    }

    @Test
    void shouldCreateTranscriptionAndPersistAggregate() {
        CreateTranscriptionCommand command = new CreateTranscriptionCommand(
                TRANSCRIPTION_ID, MEETING_ID, "raw", "clean", "{\"ok\":true}",
                0.8, NOW, NOW.plusMinutes(1)
        );

        var result = new CreateTranscriptionUseCase(transcriptionRepository).execute(command);

        assertEquals(TRANSCRIPTION_ID, result.getData().id());
        verify(transcriptionRepository).save(any(Transcription.class));
    }

    @Test
    void shouldFindTranscriptionOrReturnNotFound() {
        when(transcriptionRepository.findById(any()))
                .thenReturn(Optional.of(transcription()), Optional.empty());
        FindTranscriptionByIdUseCase useCase = new FindTranscriptionByIdUseCase(transcriptionRepository);

        assertEquals(TRANSCRIPTION_ID, useCase.execute(TRANSCRIPTION_ID).getData().id());
        assertTrue(useCase.execute(TRANSCRIPTION_ID).isFailure());
    }

    @Test
    void shouldUpdateTranscriptionOrReturnNotFound() {
        Transcription transcription = transcription();
        when(transcriptionRepository.findById(any()))
                .thenReturn(Optional.of(transcription), Optional.empty());
        UpdateTranscriptionUseCase useCase = new UpdateTranscriptionUseCase(transcriptionRepository);

        var result = useCase.execute(
                TranscriptionId.of(TRANSCRIPTION_ID),
                UpdateTranscriptionCommand.builder()
                        .cleanText("updated")
                        .formattedText(TranscriptJson.of("{\"updated\":true}"))
                        .modelConfidence(TranscriptionConfidence.of(0.6))
                        .build()
        );
        assertEquals("updated", result.getData().cleanText());
        verify(transcriptionRepository).save(transcription);

        assertTrue(useCase.execute(
                TranscriptionId.of(TRANSCRIPTION_ID), UpdateTranscriptionCommand.builder().build()
        ).isFailure());
    }
}

package br.com.meetingdecoder.domain.transcription.repository;

import br.com.meetingdecoder.domain.transcription.model.Transcription;
import br.com.meetingdecoder.domain.transcription.valueobject.MeetingId;
import br.com.meetingdecoder.domain.transcription.valueobject.TranscriptionId;

import java.util.List;
import java.util.Optional;

public interface ITranscriptionRepository {
    Transcription save(Transcription client);
    boolean existsById(TranscriptionId id);
    boolean existsByName(String name);
    List<Transcription> findAll();
    Optional<Transcription> findById(TranscriptionId id);
    void deleteById(TranscriptionId id);
}

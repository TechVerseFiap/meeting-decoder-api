package br.com.meetingdecoder.domain.transcription.repository;

import br.com.meetingdecoder.domain.transcription.model.Meeting;
import br.com.meetingdecoder.domain.transcription.valueobject.MeetingId;

import java.util.List;
import java.util.Optional;

public interface IMeetingRepository {
    Meeting save(Meeting client);
    boolean existsById(MeetingId id);
    boolean existsByName(String name);
    List<Meeting> findAll();
    Optional<Meeting> findById(MeetingId id);
    void deleteById(MeetingId id);
}

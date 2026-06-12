package br.com.meetingdecoder.infrastructure.persistence.repository;

import br.com.meetingdecoder.domain.sale.valueobject.ClientId;
import br.com.meetingdecoder.domain.sale.valueobject.SellerId;
import br.com.meetingdecoder.domain.transcription.model.Meeting;
import br.com.meetingdecoder.domain.transcription.repository.IMeetingRepository;
import br.com.meetingdecoder.domain.transcription.valueobject.*;
import br.com.meetingdecoder.infrastructure.persistence.entity.MeetingEntity;
import br.com.meetingdecoder.infrastructure.persistence.jpa.JpaMeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MeetingRepositoryAdapter implements IMeetingRepository {

    private final JpaMeetingRepository jpa;

    @Override
    public Meeting save(Meeting meeting) {
        MeetingEntity entity = toEntity(meeting);
        MeetingEntity saved = jpa.save(entity);
        return toDomain(saved);
    }

    @Override
    public boolean existsById(MeetingId id) {
        return jpa.existsById(id.value());
    }

    @Override
    public boolean existsByName(String name) {
        return jpa.existsByExternalId(name);
    }

    @Override
    public List<Meeting> findAll() {
        return jpa.findAll().stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public Optional<Meeting> findById(MeetingId id) {
        return jpa.findById(id.value())
                .map(this::toDomain);
    }

    @Override
    public void deleteById(MeetingId id) {
        jpa.deleteById(id.value());
    }

    private MeetingEntity toEntity(Meeting meeting) {
        return MeetingEntity.builder()
                .id(meeting.id().value())
                .externalId(meeting.externalId())
                .meetingDate(meeting.meetingDate())
                .startTime(meeting.meetingPeriod().startTime())
                .endTime(meeting.meetingPeriod().endTime())
                .durationInSeconds(meeting.durationInSeconds())
                .status(meeting.status())
                .external(meeting.external())
                .recordingUrl(meeting.recordingUrl().value())
                .sellerId(meeting.participants().sellerId().value())
                .clientId(meeting.participants().clientId().value())
                .createdAt(meeting.createdAt())
                .updatedAt(meeting.updatedAt())
                .build();
    }

    private Meeting toDomain(MeetingEntity entity) {
        return Meeting.create(
                MeetingId.of(entity.getId()),
                entity.getExternalId(),
                entity.getMeetingDate(),
                MeetingPeriod.of(entity.getStartTime(), entity.getEndTime()),
                entity.getStatus(),
                entity.getExternal(),
                RecordingUrl.of(entity.getRecordingUrl()),
                Participants.of(
                        SellerId.of(entity.getSellerId()),
                        ClientId.of(entity.getClientId())
                )
        );
    }
}

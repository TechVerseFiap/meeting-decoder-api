package br.com.meetingdecoder.application.ports.out;

import br.com.meetingdecoder.application.dto.QueryOptions;
import br.com.meetingdecoder.domain.model.ServiceRecommendation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ServiceRecommendationRepository {
    ServiceRecommendation save(ServiceRecommendation serviceRecommendation);
    boolean existsById(UUID id);
    Optional<ServiceRecommendation> findById(UUID id);
    List<ServiceRecommendation> findAll(QueryOptions queryOptions);
    void deleteById(UUID id);
}

package br.com.meetingdecoder.application.ports.out;

import br.com.meetingdecoder.application.dto.QueryOptions;
import br.com.meetingdecoder.domain.model.ServiceRecommendation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IServiceRecommendationRepository {
    ServiceRecommendation save(ServiceRecommendation serviceRecommendation);
    boolean existsById(UUID id);
    boolean existsByName(String name);
    Optional<ServiceRecommendation> findById(UUID id);
    List<ServiceRecommendation> findAll(QueryOptions queryOptions);
    void deleteById(UUID id);
}

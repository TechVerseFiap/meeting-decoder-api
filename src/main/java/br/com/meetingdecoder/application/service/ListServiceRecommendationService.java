package br.com.meetingdecoder.application.service;

import br.com.meetingdecoder.application.dto.QueryOptions;
import br.com.meetingdecoder.application.dto.ServiceRecommendationResponse;
import br.com.meetingdecoder.application.ports.in.serviceRecommendation.ListServiceRecommendationUseCase;
import br.com.meetingdecoder.application.ports.out.ServiceRecommendationRepository;
import br.com.meetingdecoder.application.shared.result.Result;
import br.com.meetingdecoder.domain.model.ServiceRecommendation;

import java.util.ArrayList;
import java.util.List;

public class ListServiceRecommendationService implements ListServiceRecommendationUseCase {

    private final ServiceRecommendationRepository serviceRecommendationRepository;

    public ListServiceRecommendationService (ServiceRecommendationRepository serviceRecommendationRepository) {
        this.serviceRecommendationRepository = serviceRecommendationRepository;
    }

    @Override
    public Result<List<ServiceRecommendationResponse>> all(QueryOptions queryOptions) {
        List<ServiceRecommendation> list = serviceRecommendationRepository.findAll(queryOptions);
        List<ServiceRecommendationResponse> responseList = ServiceRecommendationResponse.from(list);
        return Result.success(responseList);
    }
}

package br.com.meetingdecoder.application.service;

import br.com.meetingdecoder.application.dto.QueryOptions;
import br.com.meetingdecoder.application.dto.ServiceRecommendationResponse;
import br.com.meetingdecoder.application.ports.in.serviceRecommendation.IListServiceRecommendationUseCase;
import br.com.meetingdecoder.application.ports.out.IServiceRecommendationRepository;
import br.com.meetingdecoder.application.shared.result.Result;
import br.com.meetingdecoder.domain.model.ServiceRecommendation;

import java.util.List;

public class ListServiceRecommendationUseCase implements IListServiceRecommendationUseCase {

    private final IServiceRecommendationRepository serviceRecommendationRepository;

    public ListServiceRecommendationUseCase(IServiceRecommendationRepository serviceRecommendationRepository) {
        this.serviceRecommendationRepository = serviceRecommendationRepository;
    }

    @Override
    public Result<List<ServiceRecommendationResponse>> all(QueryOptions queryOptions) {
        List<ServiceRecommendation> list = serviceRecommendationRepository.findAll(queryOptions);
        List<ServiceRecommendationResponse> responseList = ServiceRecommendationResponse.from(list);
        return Result.success(responseList);
    }
}

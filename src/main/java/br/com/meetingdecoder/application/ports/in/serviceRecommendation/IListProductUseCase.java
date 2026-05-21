package br.com.meetingdecoder.application.ports.in.serviceRecommendation;

import br.com.meetingdecoder.application.dto.QueryOptions;
import br.com.meetingdecoder.application.dto.ProductResponse;
import br.com.meetingdecoder.application.shared.result.Result;

import java.util.List;

public interface IListProductUseCase {
    Result<List<ProductResponse>> all(QueryOptions queryOptions);
}

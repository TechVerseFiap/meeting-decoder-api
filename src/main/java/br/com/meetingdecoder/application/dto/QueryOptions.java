package br.com.meetingdecoder.application.dto;

import br.com.meetingdecoder.application.shared.enums.SortDirection;
import br.com.meetingdecoder.domain.enums.ServiceCategory;

public record QueryOptions(
        SortDirection direction,
        ServiceCategory category
) {
}

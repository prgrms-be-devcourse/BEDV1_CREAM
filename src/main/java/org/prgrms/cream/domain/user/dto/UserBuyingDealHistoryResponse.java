package org.prgrms.cream.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.prgrms.cream.domain.deal.dto.DealHistoryResponse;

public record UserBuyingDealHistoryResponse(
	@JsonProperty(value = "dealHistory") List<DealHistoryResponse> dealHistoryResponses
) {

}

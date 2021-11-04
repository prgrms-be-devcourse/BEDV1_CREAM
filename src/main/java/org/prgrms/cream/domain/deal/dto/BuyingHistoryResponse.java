package org.prgrms.cream.domain.deal.dto;

import java.util.List;

public record BuyingHistoryResponse(
	List<BuyingBidResponse> buyingBidResponses
) {

}

package org.prgrms.cream.domain.deal.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class SellingHistoryResponse {

	private List<SellingBidResponse> userSellingBidResponses;


	public SellingHistoryResponse(
		List<SellingBidResponse> userSellingBidResponses
	) {
		this.userSellingBidResponses = userSellingBidResponses;
	}
}

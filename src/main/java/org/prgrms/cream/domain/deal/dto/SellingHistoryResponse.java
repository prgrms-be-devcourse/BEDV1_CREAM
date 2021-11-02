package org.prgrms.cream.domain.deal.dto;

import java.util.List;
import lombok.Getter;
import org.prgrms.cream.domain.user.dto.UserDealResponse;

@Getter
public class SellingHistoryResponse {

	private List<SellingBidResponse> sellingBidResponse;
	private List<UserDealResponse> ongoingUserDealResponse;
	private List<UserDealResponse> completeUserDealResponse;

	public SellingHistoryResponse(
		List<SellingBidResponse> sellingBidResponse,
		List<UserDealResponse> ongoingUserDealResponse,
		List<UserDealResponse> completeUserDealResponse
	) {
		this.sellingBidResponse = sellingBidResponse;
		this.ongoingUserDealResponse = ongoingUserDealResponse;
		this.completeUserDealResponse = completeUserDealResponse;
	}
}

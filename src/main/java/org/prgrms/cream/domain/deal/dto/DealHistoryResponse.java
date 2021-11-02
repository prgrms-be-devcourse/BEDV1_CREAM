package org.prgrms.cream.domain.deal.dto;

import java.util.List;
import lombok.Getter;
import org.prgrms.cream.domain.user.dto.UserDealResponse;

@Getter
public class DealHistoryResponse {

	private List<UserDealResponse> userDealResponses;

	public DealHistoryResponse(List<UserDealResponse> userDealResponses) {
		this.userDealResponses = userDealResponses;
	}
}

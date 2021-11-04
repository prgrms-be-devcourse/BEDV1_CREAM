package org.prgrms.cream.domain.user.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class UserDealHistoryResponse {

	private List<UserDealResponse> userDealResponses;

	public UserDealHistoryResponse(List<UserDealResponse> userDealResponses) {
		this.userDealResponses = userDealResponses;
	}
}

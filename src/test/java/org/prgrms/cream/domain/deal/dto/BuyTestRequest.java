package org.prgrms.cream.domain.deal.dto;

public class BuyTestRequest {

	Long userId;

	public BuyTestRequest() {
	}

	public BuyTestRequest(Long userId) {
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}



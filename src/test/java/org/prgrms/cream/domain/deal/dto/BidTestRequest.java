package org.prgrms.cream.domain.deal.dto;

import javax.validation.constraints.Min;

public class BidTestRequest {

	@Min(30000)
	private Integer price;

	@Min(1)
	private Integer deadline;
	private Long userId;

	public BidTestRequest() {
	}

	public BidTestRequest(int price, int deadline, long userId) {
		this.price = price;
		this.deadline = deadline;
		this.userId = userId;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getDeadline() {
		return deadline;
	}

	public void setDeadline(int deadline) {
		this.deadline = deadline;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
}


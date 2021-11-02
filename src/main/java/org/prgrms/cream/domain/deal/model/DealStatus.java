package org.prgrms.cream.domain.deal.model;

import lombok.Getter;

@Getter
public enum DealStatus {
	BIDDING("입찰 중"),
	EXPIRED("기한만료"),
	BID_DELETED("입찰 취소"),
	BID_COMPLETED("입찰 완료"),
	UNDER_INSPECTION("검수 중"),
	SHIP_COMPLETED("배송완료");

	private final String status;

	DealStatus(String status) {
		this.status = status;
	}
}

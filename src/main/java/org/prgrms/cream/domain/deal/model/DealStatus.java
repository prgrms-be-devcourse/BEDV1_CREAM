package org.prgrms.cream.domain.deal.model;

import lombok.Getter;

@Getter
public enum DealStatus {
	BIDDING("입찰 중"),
	EXPIRED("기한만료"),
	BID_CANCELLED("입찰 취소"),
	BID_COMPLETED("입찰 완료"),
	UNDER_INSPECTION("검수 중"),
	INSPECTION_PASSED("검수합격"),
	IN_TRANSIT("배송중"),
	HOLD("보류"),
	SHIP_COMPLETED("배송완료"),
	PAYMENT_COMPLETED("정산완료"),
	;

	private final String status;

	DealStatus(String status) {
		this.status = status;
	}
}

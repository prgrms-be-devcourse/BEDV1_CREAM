package org.prgrms.cream.domain.deal.dto;

public record BidResponse(
	int price,
	int deadline,
	String expiredDate
) {

}

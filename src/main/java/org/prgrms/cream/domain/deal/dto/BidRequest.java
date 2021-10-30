package org.prgrms.cream.domain.deal.dto;

public record BidRequest(
	int price,
	int deadline,
	long userId
) {

}

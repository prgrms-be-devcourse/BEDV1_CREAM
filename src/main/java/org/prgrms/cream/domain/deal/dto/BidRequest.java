package org.prgrms.cream.domain.deal.dto;

import javax.validation.constraints.Min;

public record BidRequest(
	@Min(30000) int price,
	@Min(1) int deadline,
	long userId
) {

}
